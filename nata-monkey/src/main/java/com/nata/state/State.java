package com.nata.state;

import com.nata.element.Widget;

import java.util.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-24 15:17
 */
public class State {
    private String appPackage = null;
    private String activity = null;
    private List<Widget> widgetList = null;

    // the times that enter int this state
    private int visit;
    private final double FirstReward = 2.0;

    private ArrayList<ActionEdge> fromEdges;
    private ArrayList<ActionEdge> outEdges;

    public State(String appPackage,String activity,List<Widget> widgetList) {
        this.appPackage = appPackage;
        this.activity = activity;
        this.widgetList = widgetList;
        this.visit = 1;

        fromEdges = new ArrayList<>();
        outEdges = new ArrayList<>();
    }

    public ArrayList<ActionEdge> getFromEdges() {
        return fromEdges;
    }

    public ArrayList<ActionEdge> getOutEdges() {
        return outEdges;
    }

    public void addFromEdge(ActionEdge edge){
        fromEdges.add(edge);
    }

    public void addToEdge(ActionEdge edge){
        outEdges.add(edge);
    }




    private int getAvailableActionCount(){
        int cnt = 0;
        for (Widget widget:widgetList) {
            if(widget.getEnabled().equals("true")){
                if(widget.getClickable().equals("true") || widget.getScrollable().equals("true") || widget.getLong_clickable().equals("true")){
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public String getAppPackage(){
        return  appPackage;
    }

    public String getActivity(){
        return activity;
    }

    public double getReward(){
        if(visit == 1){
            return FirstReward;
        }else{
            // make state with more available widgets more attractive
            return (1.0 + 0.01 * getAvailableActionCount())/visit;
        }
    }

    public void increaseVisit(){
        visit++;
    }

    public List<Widget> getWidgetSet(){
        return widgetList;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + appPackage.hashCode();
        hash = 31 * hash + activity.hashCode();
        for (Widget node : widgetList) {
            hash = 31 * hash + node.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null) {
            return false;
        }

        if (getClass() != otherObject.getClass()) {
            return false;
        }

        State other = (State) otherObject;
        if(!appPackage.equals(other.appPackage)){
            return false;
        }
        if(!activity.equals(other.activity)){
           return false;
        }
        if (widgetList.size() != other.widgetList.size()) {
            return false;
        }
        if(!other.widgetList.containsAll(widgetList)){
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "[State]@" +hashCode() +"{" +
                "appPackage='" + appPackage + '\'' +
                ", activity='" + activity + '\'' +
                ",uiList='" + widgetList +
                '}';
    }
}
