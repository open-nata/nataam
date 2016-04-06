package com.nata.action;

import java.util.Objects;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 16:36
 */
public abstract class Action {
    protected String type;
    protected int count = 0;
    protected final double BASE = 2.0;

    public Action(String type) {
        this.type = type;
    }

    /**
     * Implement how to fire the action
     */
    abstract public void fire();

    /**
     * Give the rule how to reward the action
     * @return reward value
     */
    abstract public double getReward();
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(type);
//    }
//
//    @Override
//    public boolean equals(Object otherObject) {
//        if (this == otherObject) {
//            return true;
//        }
//
//        if (otherObject == null) {
//            return false;
//        }
//
//        if (getClass() != otherObject.getClass()) {
//            return false;
//        }
//
//        Action other = (Action) otherObject;
//        return Objects.equals(type, other.type) ;
//    }


    @Override
    public String toString() {
        return "[Action]("+type+","+count +")";
    }
}
