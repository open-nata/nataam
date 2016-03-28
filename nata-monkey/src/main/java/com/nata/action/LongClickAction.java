package com.nata.action;

import com.nata.cmd.AdbDevice;
import com.nata.element.Element;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-24 21:23
 */
public class LongClickAction extends Action{

    private AdbDevice device = null;
    private Element element = null;
    public LongClickAction(AdbDevice device,Element element){
        super.setName(ActionType.LONGCLICK);
        this.device = device;
        this.element = element;
    }

    @Override
    public void fire() {
        device.longPress(element.getX(),element.getY());
        count++;
    }


    @Override
    public String toString() {
        return super.toString()+"LongClickAction{" +
                "element=" + element +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + element.hashCode();
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

        LongClickAction other = (LongClickAction) otherObject;

        if(!element.equals(other.element)){
            return false;
        }

        return true;
    }
}
