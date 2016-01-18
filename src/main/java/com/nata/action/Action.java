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
    private Element element = null;

    public Action(Element e) {
        this.name = ActionType.UNKNOWN;
        this.element = e;
    }

    protected void setName(String name){
       this.name = name;
    }

    abstract public boolean fire();

    @Override
    public int hashCode() {
        return Objects.hash(name, element);
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
        return Objects.equals(name, other.name) && Objects.equals(element, other.element);
    }

    @Override
    public String toString() {
        return getClass().getName() + "[name=" + name + ",element=" + element + "]";
    }
}
