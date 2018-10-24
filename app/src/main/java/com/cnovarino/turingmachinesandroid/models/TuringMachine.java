package com.cnovarino.turingmachinesandroid.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TuringMachine {

    private int step;
    private int cursor;
    private State initial_state;
    private State current_state;
    private List<State> states;
    private Map<Integer,Boolean> tape;

    private boolean exec_finished;
    private boolean exec_error;
    private boolean exec_paused;

    public TuringMachine() {
        states = new ArrayList<>();
        tape = new TreeMap<>();

        tape.put(0,Boolean.FALSE);

        exec_finished = false;
        exec_error = false;
        exec_paused = false;

        step = 0;
        cursor = 0;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public State getInitial_state() {
        return initial_state;
    }

    public void setInitial_state(State initial_state) {
        this.initial_state = initial_state;
    }

    public State getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(State current_state) {
        this.current_state = current_state;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public Map<Integer, Boolean> getTape() {
        return tape;
    }

    public void setTape(Map<Integer, Boolean> tape) {
        this.tape = tape;
    }

    public boolean isExec_finished() {
        return exec_finished;
    }

    public void setExec_finished(boolean exec_finished) {
        this.exec_finished = exec_finished;
    }

    public boolean isExec_error() {
        return exec_error;
    }

    public void setExec_error(boolean exec_error) {
        this.exec_error = exec_error;
    }

    public boolean isExec_paused() {
        return exec_paused;
    }

    public void setExec_paused(boolean exec_paused) {
        this.exec_paused = exec_paused;
    }

    public void resetMachine(){
        step = 0;
        cursor = 0;
        current_state = initial_state;
        tape.clear();
        tape.put(0,Boolean.FALSE);
        exec_paused = false;
        exec_error = false;
        exec_finished = false;
    }

    @Override
    public String toString() {

        StringBuilder table = new StringBuilder();
        table.append("\t 0\t 1\n");

        for (State state : states) {

            if(initial_state != null)
                if(state.equals(initial_state))
                    table.append("*");

            table.append(state).append("\n");
        }

        return table.toString();
    }

    public String printTape(){

        StringBuilder result = new StringBuilder();

        for(int key : tape.keySet()){
            String val = tape.get(key) ? "1" : "0";

            result.append((key == cursor) ? ("<font color='black'><b>" + val + "</b></font>") : val);
            if(key == cursor)
                result.append("<font color='black'><b>")
                        .append(current_state.getName())
                        .append("</b></font>");
        }

        return result.toString();
    }


}
