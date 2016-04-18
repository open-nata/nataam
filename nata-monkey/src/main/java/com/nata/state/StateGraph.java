package com.nata.state;

import com.nata.action.Action;
import com.nata.utils.HttpUtil;
import com.nata.utils.LogUtil;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-13 15:15
 */
public class StateGraph {
//    private HashMap<State,Integer> st;
//    private State[] keys;
//    private Graph G;

    private ArrayList<ActionEdge> edges;
    private ArrayList<State> states;

    private State rootState = null;
    private State curState;

    private int stateCount = 0;

    public StateGraph(){
        edges = new ArrayList<>();
        states = new ArrayList<>();
//        st = new HashMap<>();
    }

    public void addRootState(State state){
        if(rootState == null){
            rootState = state;
            stateCount++;
            curState = rootState;
        }
    }

//    public void addEdge(State toState, Action action ){
//        ActionEdge edge = new ActionEdge(curState,toState,action);
////        edges.add(edge);
//        states.add(toState);
//    }

//    public void printActionEdge(){
//        for (ActionEdge edge: edges) {
//          LogUtil.debug(edge.getFromState().hashCode() +" ----" + edge.getFireAction() + "-------->"+ edge.getToState().hashCode());
//        }
//    }

//    public void makeGraph(){
//        for (ActionEdge edge: edges) {
//            State fromState = edge.getFromState();
//            State toState = edge.getToState();
//            if(!st.containsKey(fromState)){
//                st.put(fromState,st.size());
//            }
//            if(!st.containsKey(toState)){
//                st.put(toState,st.size());
//            }
//        }
//
//        keys = new State[st.size()];
//
//        for(State state : st.keySet()){
//            keys[st.get(state)] = state;
//        }
//
//        G = new Graph(st.size());
//    }

}
