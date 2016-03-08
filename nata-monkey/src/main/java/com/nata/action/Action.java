package com.nata.action;

import com.nata.element.Element;

import java.util.Objects;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 16:36
 */
public abstract class Action {
    private String name = null;


    public Action() {
        this.name = ActionType.UNKNOWN;
    }

    protected void setName(String name){
       this.name = name;
    }

    abstract public void fire();

    @Override
    public int hashCode() {
        return Objects.hash(name);
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

        Action other = (Action) otherObject;
        return Objects.equals(name, other.name) ;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name=" + name + "]";
    }
}
