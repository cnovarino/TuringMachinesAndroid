package com.cnovarino.turingmachinesandroid.models;

import java.util.Objects;

public class StateAction {

    private String rendering;
    private boolean end_action;
    private boolean write_val;
    private int direction_val;
    private String next_state_val;

    public StateAction(String rendering) {
        this.rendering = rendering;
    }

    public StateAction(boolean end_action, boolean write_val, int direction_val, String next_state_val) {
        this.end_action = end_action;
        this.write_val = write_val;
        this.direction_val = direction_val;
        this.next_state_val = next_state_val;
    }

    @Override
    public String toString() {

        if(!end_action)
            return rendering.toLowerCase();
        else
            return "FIN";
    }

    public String getRendering() {
        return rendering;
    }

    public void setRendering(String rendering) {
        this.rendering = rendering;
    }

    public boolean isEnd_action() {
        return end_action;
    }

    public void setEnd_action(boolean end_action) {
        this.end_action = end_action;
    }

    public int getDirection_val() {
        return direction_val;
    }

    public void setDirection_val(int direction_val) {
        this.direction_val = direction_val;
    }

    public String getNext_state_val() {
        return next_state_val;
    }

    public void setNext_state_val(String next_state_val) {
        this.next_state_val = next_state_val;
    }

    public boolean getWrite_val() {
        return write_val;
    }

    public void setWrite_val(boolean write_val) {
        this.write_val = write_val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateAction that = (StateAction) o;
        return Objects.equals(rendering, that.rendering);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rendering);
    }

    public boolean validateStateAction(){

        if(rendering.toUpperCase().equals("FIN")){
            setEnd_action(true);
            setRendering("FIN");
            return true;
        }

        if(rendering.length() < 3)
            return false;

        char write_char = rendering.charAt(0);

        if(write_char != '0' && write_char != '1')
            return false;

        boolean temp_write_val = (write_char == '1');

        char direction_char = rendering.charAt(1);

        if(direction_char != '<' && direction_char != '>')
            return false;

        int temp_direction_val = (direction_char == '<') ? -1 : 1;

        String temp_next_val = rendering.substring(2).toLowerCase();

        setWrite_val(temp_write_val);
        setDirection_val(temp_direction_val);
        setNext_state_val(temp_next_val);
        setEnd_action(false);

        return true;
    }
}
