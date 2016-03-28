package com.nata.action;

import com.nata.cmd.AdbDevice;
import com.nata.element.Element;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 16:48
 */
public class TapAction extends Action{
    private AdbDevice device = null;
    private Element element = null;
    public TapAction(AdbDevice device,Element element){
        super.setName(ActionType.TAP);
        this.device = device;
        this.element = element;
    }

    @Override
    public void fire() {
        device.tap(element.getX(),element.getY());
        count++;
    }


    @Override
    public String toString() {
        return super.toString()+"TapAction{" +
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

        TapAction other = (TapAction) otherObject;

        if(!element.equals(other.element)){
            return false;
        }

        return true;
    }
}
