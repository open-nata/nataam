package com.nata.action;

import com.nata.cmd.AdbDevice;
import com.nata.element.Widget;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-24 21:23
 */
public class LongClickAction extends Action{
    private AdbDevice device = null;
    private Widget widget = null;

    private final double LONG_CLICK_REWARD = BASE - 0.2;

    public LongClickAction(AdbDevice device,Widget widget){
        super(ActionType.LONG_CLICK);
        this.device = device;
        this.widget = widget;
    }

    @Override
    public void fire() {
        device.longPress(widget.getX(), widget.getY());
        count++;
    }

    @Override
    public double getReward() {
        if(count == 0){
            return LONG_CLICK_REWARD;
        }else{
            return 1.0/count;
        }
    }


    @Override
    public String toString() {
        return super.toString()+"@"+widget;
    }

//    @Override
//    public int hashCode() {
//        int hash = 17;
//        hash = 31 * hash + type.hashCode();
//        hash = 31 * hash + widget.hashCode();
//        return hash;
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
//        LongClickAction other = (LongClickAction) otherObject;
//
//        if(!type.equals(other.type)){
//            return false;
//        }
//
//        if(!widget.equals(other.widget)){
//            return false;
//        }
//
//        return true;
//    }
}
