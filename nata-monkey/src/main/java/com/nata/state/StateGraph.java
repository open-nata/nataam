package com.nata.state;

import com.nata.action.Action;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-13 15:15
 */
public class StateGraph {
    private HashMap<State,Integer> st;
    private State[] keys;
    private Graph G;

    private ArrayList<ActionEdge> edges;

    public StateGraph(){
        edges = new ArrayList<>();
        st = new HashMap<>();
    }

    public void addEdge(State fromState, State toState, Action action ){
        ActionEdge edge = new ActionEdge(fromState,toState,action);
        edges.add(edge);
    }

}
