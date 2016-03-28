package com.nata.state;

import com.nata.element.UINode;

import java.util.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-24 15:17
 */
public class State {
    private String appPackage = null;
    private String activity = null;
    private Set<UINode> uiList = new HashSet<>();

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

    public void addUINode(UINode node) {
        uiList.add(node);
    }

    public Set<UINode> getUIList(){
        return uiList;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + appPackage.hashCode();
        hash = 31 * hash + activity.hashCode();
        for (UINode node : uiList) {
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
        if (uiList.size() != other.uiList.size()) {
            return false;
        }
        for (UINode node : uiList) {
            if (!other.uiList.contains(node)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "[State]@" +hashCode() +"{" +
                "appPackage='" + appPackage + '\'' +
                ", activity='" + activity +
                '}';
    }
}
