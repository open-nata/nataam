package com.nata.state;

import com.nata.element.UINode;

import java.util.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-24 15:17
 */
public class State {
    private String activity = null;
    private Set<UINode> uiList = new HashSet<>();

    public State(String activity) {
        this.activity = activity;
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

}
