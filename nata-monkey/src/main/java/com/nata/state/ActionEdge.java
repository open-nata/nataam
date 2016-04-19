package com.nata.state;

import com.nata.action.Action;

import java.util.ArrayList;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-13 15:35
 */
public class ActionEdge {
    private DFSState fromState;
    private DFSState toState;
    private ArrayList<Action> actions;

    public ActionEdge(DFSState fromState, DFSState toState, ArrayList<Action> actions) {
        this.fromState = fromState;
        this.toState = toState;
        this.actions = new ArrayList<Action>(actions);

        fromState.addToEdge(this);
        toState.setFromEdge(this);
    }

    public DFSState getFromState() {
        return fromState;
    }

    public DFSState getToState() {
        return toState;
    }

    public ArrayList<Action> getFireActions() {
        return actions;
    }

    public void fire(){
        for (Action action: actions) {
            action.fire();
        }
    }

    @Override
    public String toString() {
        return "ActionEdge{" +
                "fromState=" + fromState +
                ", toState=" + toState +
                ", actions=" + actions +
                '}';
    }
}
