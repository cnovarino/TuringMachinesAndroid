package com.cnovarino.turingmachinesandroid.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnovarino.turingmachinesandroid.R;

import java.util.ArrayList;


public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    private ArrayList<String> logs;
    private LayoutInflater inflater;


    public LogAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        logs = new ArrayList<>();
    }

    public void addLog(String log){
        logs.add(log);
        notifyDataSetChanged();
    }

    public void clear(){
        logs.clear();
        notifyDataSetChanged();
    }

    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.log_item,null);
        return new LogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, final int position) {

        String log = logs.get(position);
//        holder.txt_log.setText(Html.fromHtml("Paso " + String.valueOf(step.getStep()) + ": " + step.getTape_snapshot()));
        holder.txt_log.setText(Html.fromHtml(log));

    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    class LogViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_log;

        private LogViewHolder(View itemView) {
            super(itemView);
            txt_log = itemView.findViewById(R.id.txt_log);

        }
    }
}
