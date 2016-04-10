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

    public State(String appPackage,String activity,List<Widget> widgetList) {
        this.appPackage = appPackage;
        this.activity = activity;
        this.widgetList = widgetList;
        this.visit = 1;
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
            return 0.01 * widgetList.size()/visit;
        }
    }

    public void increaseVisit(){
        visit++;
    }

//    public void addWidget(Widget node) {
//        widgetSet.add(node);
//    }

//    public Widget getWidgetByResourceId(String resourceId){
//       Iterator iterator = widgetSet.iterator();
//        while(iterator.hasNext()){
//            Widget widget = (Widget)iterator.next();
//            if(widget.getResourceId().equals(resourceId)){
//                return widget;
//            }
//        }
//        return null;
//    }

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
