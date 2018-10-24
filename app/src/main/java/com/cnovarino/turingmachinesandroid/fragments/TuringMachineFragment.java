package com.cnovarino.turingmachinesandroid.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnovarino.turingmachinesandroid.R;
import com.cnovarino.turingmachinesandroid.adapters.LogAdapter;
import com.cnovarino.turingmachinesandroid.listener.OnFragmentInteractionListener;
import com.cnovarino.turingmachinesandroid.managers.TuringMachineManager;
import com.cnovarino.turingmachinesandroid.models.State;
import com.cnovarino.turingmachinesandroid.models.StateAction;
import com.cnovarino.turingmachinesandroid.models.TuringMachine;

public class TuringMachineFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    LinearLayout machine_layout;
    LinearLayout container_layout;

    RecyclerView recyclerView;
    LogAdapter adapter;

    public TuringMachineFragment() {
        this.setRetainInstance(true);
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_turing_machine, container, false);

        recyclerView = v.findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);

        recyclerView.setLayoutManager(mLayoutManager);
        if(recyclerView.getItemDecorationCount() == 0)
            recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        adapter = new LogAdapter(getContext());

        recyclerView.setAdapter(adapter);

        Button.class.cast(v.findViewById(R.id.btn_read_qr)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onReadQR();
            }
        });

        machine_layout = v.findViewById(R.id.machine_layout);
        container_layout = v.findViewById(R.id.container_layout);

        final EditText edit_steps = v.findViewById(R.id.edit_steps);
        Button btn_execute = v.findViewById(R.id.btn_execute);
        Button btn_reset = v.findViewById(R.id.btn_reset);

        loadCurrentMachine();

        btn_execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exec_machine(Integer.valueOf(edit_steps.getText().toString()));
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TuringMachineManager.tm != null)
                    TuringMachineManager.tm.resetMachine();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void exec_machine(int steps){
        TuringMachine tm = TuringMachineManager.tm;

        while (!tm.isExec_error() && !tm.isExec_finished() && steps-- > 0){

            adapter.addLog("Paso " + String.valueOf(tm.getStep()) + ": " + tm.printTape());

            tm.setStep(tm.getStep()+1);

            StateAction action = tm.getCurrent_state().getAction(tm.getTape().get(tm.getCursor()));

            if(action.isEnd_action()){

                if(tm.isExec_finished()){
                    adapter.addLog("La maquina finalizo su ejecucion en el paso " + String.valueOf(tm.getStep()));
                }else{
                    adapter.addLog("La maquina sigue ejecutandose luego de " + String.valueOf(tm.getStep()) + " pasos.");
                }

                adapter.addLog("Posicion del Cursor " + String.valueOf(tm.getCursor()));
                adapter.addLog("Cinta " + tm.printTape());

                tm.setExec_finished(true);
                continue;
            }

            tm.getTape().put(tm.getCursor(),action.getWrite_val());
            tm.setCursor(tm.getCursor()+action.getDirection_val());


            if(!tm.getTape().containsKey(tm.getCursor()))
                tm.getTape().put(tm.getCursor(),Boolean.FALSE);

            int next_state_index = tm.getStates().indexOf(new State(action.getNext_state_val()));

            if(next_state_index == -1){
                tm.setExec_error(true);
                adapter.addLog("Error de ejecucion en el paso " + String.valueOf(tm.getStep()) + ". COMO PASO ?<br>");
                continue;
            }

            tm.setCurrent_state(tm.getStates().get(next_state_index));

            if(steps == -1 && !tm.isExec_finished() && !tm.isExec_error()){
                adapter.addLog("La maquina sigue ejecutandose despues de " + String.valueOf(tm.getStep()) + " pasos. Presione ejecutar nuevamente para continuar.");
                adapter.addLog("Posicion del Cursor " + String.valueOf(tm.getCursor()));
                adapter.addLog("Cinta  " + tm.printTape());
            }


        }

    }

    public void loadCurrentMachine(){

        if(TuringMachineManager.tm == null)
            return;

        if(container_layout.getChildCount() > 0)
            container_layout.removeAllViews();
        machine_layout.setVisibility(View.VISIBLE);

        for (State state : TuringMachineManager.tm.getStates()){

            LinearLayout.LayoutParams new_row_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout new_row = new LinearLayout(getContext());
            new_row.setLayoutParams(new_row_params);
            new_row.setOrientation(LinearLayout.HORIZONTAL);
            new_row.setWeightSum(100);

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,20);
            TextView state_name = new TextView(getContext());
            state_name.setText(state.getName());
            state_name.setLayoutParams(params1);
            state_name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            state_name.setTextSize(16);

            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,40);
            TextView on_zero_action = new TextView(getContext());
            on_zero_action.setText(state.getOn_zero().toString());
            on_zero_action.setLayoutParams(params2);
            on_zero_action.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            on_zero_action.setTextSize(16);

            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,40);
            TextView on_one_action = new TextView(getContext());
            on_one_action.setText(state.getOn_one().toString());
            on_one_action.setLayoutParams(params3);
            on_one_action.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            on_one_action.setTextSize(16);

            new_row.addView(state_name);
            new_row.addView(on_zero_action);
            new_row.addView(on_one_action);

            container_layout.addView(new_row);

            adapter.clear();

        }



    }
}
