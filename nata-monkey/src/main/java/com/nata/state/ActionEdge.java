package com.nata.state;

import com.nata.action.Action;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-13 15:35
 */
public class ActionEdge {
    private State fromState;
    private State toState;
    private Action fireAction;

    public ActionEdge(State fromState, State toState, Action action) {
        this.fromState = fromState;
        this.toState = toState;
        this.fireAction = action;

        fromState.addToEdge(this);
        toState.addFromEdge(this);
    }

    public State getFromState() {
        return fromState;
    }

    public State getToState() {
        return toState;
    }

    public Action getFireAction() {
        return fireAction;
    }

    public void fire(){
        fireAction.fire();
    }

    @Override
    public String toString() {
        return "ActionEdge{" +
                "fromState=" + fromState +
                ", toState=" + toState +
                ", fireAction=" + fireAction +
                '}';
    }
}
