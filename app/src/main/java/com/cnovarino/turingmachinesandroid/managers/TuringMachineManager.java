package com.cnovarino.turingmachinesandroid.managers;

import com.cnovarino.turingmachinesandroid.models.State;
import com.cnovarino.turingmachinesandroid.models.StateAction;
import com.cnovarino.turingmachinesandroid.models.TuringMachine;

import java.util.List;

public class TuringMachineManager {

    public static TuringMachine tm;

    public static int deserialize(String machine_code){

        String[] states = machine_code.trim().split("\\|");

        if(states.length == 0)
            return -1;

        tm = new TuringMachine();

        for (String serialized_state : states){

            String[] deserialized = serialized_state.split(";");

            if(deserialized.length != 3)
                return  -2;

            String state_name = deserialized[0];
            StateAction on_zero = new StateAction(deserialized[1]);

            if(!on_zero.validateStateAction())
                return -3;

            StateAction on_one = new StateAction(deserialized[2]);

            if(!on_one.validateStateAction())
                return -3;

            tm.getStates().add(new State(state_name,on_zero,on_one));
            tm.setInitial_state(tm.getStates().get(0));
            tm.setCurrent_state(tm.getStates().get(0));
        }

        return 1;
    }
}
