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
    private Set<Widget> widgetSet= new HashSet<>();

    public State(String appPackage,String activity) {
        this.appPackage = appPackage;
        this.activity = activity;
    }

    public String getAppPackage(){
        return  appPackage;
    }

    public String getActivity(){
        return activity;
    }

    public void addWidget(Widget node) {
        widgetSet.add(node);
    }

    public Widget getWidgetByResourceId(String resourceId){
       Iterator iterator = widgetSet.iterator();
        while(iterator.hasNext()){
            Widget widget = (Widget)iterator.next();
            if(widget.getResourceId().equals(resourceId)){
                return widget;
            }
        }
        return null;
    }

    public Set<Widget> getWidgetSet(){
        return widgetSet;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + appPackage.hashCode();
        hash = 31 * hash + activity.hashCode();
        for (Widget node : widgetSet) {
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
        if (widgetSet.size() != other.widgetSet.size()) {
            return false;
        }
        if(!other.widgetSet.containsAll(widgetSet)){
            return false;
        }
//        for (Widget node : widgetSet) {
//            if (!other.widgetSet.contains(node)) {
//                return false;
//            }
//        }

        return true;
    }

    @Override
    public String toString() {
        return "[State]@" +hashCode() +"{" +
                "appPackage='" + appPackage + '\'' +
                ", activity='" + activity + '\'' +
                ",uiList='" + widgetSet +
                '}';
    }
}
