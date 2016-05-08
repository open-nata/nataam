package com.nata.results;

import com.nata.action.Action;
import com.nata.action.StartAppAction;
import com.nata.state.ActionEdge;
import com.nata.state.DFSState;

import java.util.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-05-07 15:20
 */
public class TreeWeb {

    public static List<String> getActLunchTestcases(ArrayList<DFSState> nodes,List<Action> setupActions){

        Map<String, DFSState> map=new HashMap<>();
        List<String> actLunch = new ArrayList<>();

        //search shorter path to start this activity
        for (DFSState node : nodes) {
            int kind=node.getKind();
            if(kind==node.NORMAL||kind==node.OLD){
                String name=node.getActivity();
                if(map.containsKey(name)){
                    DFSState lState=map.get(name);
                    if(pathLength(node)<pathLength(lState)){
                        map.put(name, node);
                    }
                }else{
                    map.put(name, node);
                }
            }
        }

        for(String key:map.keySet()) {
            String s = "";
            DFSState node = map.get(key);
            s += "Goto " + key + " : \n";

            //add setup actions
            for (Action action: setupActions) {
                s += action.toCommand() + "\n";
            }

            Stack<ActionEdge> stack = new Stack<>();
            DFSState lState = node;
            while (lState.getFromEdge() != null) {
                stack.push(lState.getFromEdge());
                lState = lState.getFromEdge().getFromState();
            }

            while (!stack.empty()) {
               s += stack.pop().toCommand();
            }
            actLunch.add(s);
        }
        return actLunch;
    }

    private static int pathLength(DFSState state){
        int len=0;

        while(state.getFromEdge()!=null)
        {
            len++;
            state=state.getFromEdge().getFromState();
        }
        return len;
    }
}
