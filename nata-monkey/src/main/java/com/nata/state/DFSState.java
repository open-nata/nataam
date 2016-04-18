package com.nata.state;

import com.nata.action.Action;
import com.nata.element.Widget;

import java.util.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-18 00:06
 */
public class DFSState extends State{
    public static final int NORMAL = 1;
    public static final int OLD = 2;
    public static final int OUT = 3;
    public static final int SAME = 4;

    private int kind = 1;
    private int index = 0;

    private Iterator<Action> it;

    private ActionEdge fromEdge = null;
    private ArrayList<ActionEdge> outEdges;

    public DFSState(String appPackage, String activity, List<Widget> widgetList, List<Action> actions) {
        super(appPackage, activity, widgetList, actions);

        it = actions.iterator();
        outEdges = new ArrayList<>();
    }

    public ActionEdge getFromEdge() {
        return fromEdge;
    }

    public ArrayList<ActionEdge> getOutEdges() {
        return outEdges;
    }

    public void setFromEdge(ActionEdge edge){
        this.fromEdge = edge;
    }

    public void addToEdge(ActionEdge edge){
        outEdges.add(edge);
    }


    public boolean isNotOver(){
        return it.hasNext();
    }

    public Action getAction() {
        if (it.hasNext())
            return it.next();
        return null;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (this == o)
            return true;

        if (!(o instanceof DFSState))
            return false;


        DFSState oState = (DFSState) o;
        if (getAppPackage().equals(oState.getAppPackage())
                && actions.size() == oState.actions.size()) {
            int count=0;
            Iterator<Action> it = oState.actions.iterator();
            while (it.hasNext()) {
                if (!actions.contains(it.next()))
                    count++;
            }
            float rate=((float)count)/((float)actions.size());
            if(rate> 0.5)
                return true;
            else {
                System.out.println(this.toString());
                System.out.println("=== vs ===");
                System.out.println(o.toString());
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        Iterator<Action> it = actions.iterator();
        while (it.hasNext()) {
            Action ba = it.next();
            result += ba.hashCode();
        }
        return result;
    }


}
