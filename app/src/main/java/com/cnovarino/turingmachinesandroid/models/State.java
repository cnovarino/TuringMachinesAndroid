package com.cnovarino.turingmachinesandroid.models;

import java.util.Objects;

public class State {

    private String name;
    private StateAction on_zero;
    private StateAction on_one;

    public State(String name) {
        this.name = name;
    }

    public State(String name, StateAction on_zero, StateAction on_one) {
        this.name = name;
        this.on_zero = on_zero;
        this.on_one = on_one;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StateAction getOn_zero() {
        return on_zero;
    }

    public void setOn_zero(StateAction on_zero) {
        this.on_zero = on_zero;
    }

    public StateAction getOn_one() {
        return on_one;
    }

    public void setOn_one(StateAction on_one) {
        this.on_one = on_one;
    }

    public StateAction getAction(boolean on){
        if(on)
            return getOn_one();
        else
            return getOn_zero();
    }

    @Override
    public String toString() {
        return name + "\t" + on_zero + "\t" + on_one;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
