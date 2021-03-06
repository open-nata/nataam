package com.nata.action;

import com.nata.AdbDevice;
import com.nata.element.Widget;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-24 21:23
 */
public class LongClickAction extends Action{
    private AdbDevice device = null;
    private Widget widget = null;
    private int X;
    private int Y;

    private int startX;
    private int startY;
    private int endX;
    private int endY;


    private final double LONG_CLICK_REWARD = BASE - 1.0;

    public LongClickAction(AdbDevice device,Widget widget){
        super(ActionType.LONG_CLICK);
        this.device = device;
        this.widget = widget;
        this.X = widget.getX();
        this.Y = widget.getY();

        this.startX =  widget.getStartX();
        this.startY = widget.getStartY();
        this.endX = widget.getEndX();
        this.endY = widget.getEndY();
    }

    public LongClickAction(AdbDevice device,int startX, int startY, int endX, int endY,int X, int Y){
        super(ActionType.LONG_CLICK);
        this.device = device;
        this.X = X;
        this.Y = Y;

        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void fire() {
        device.longPress(X, Y);
        count++;
    }

    @Override
    public double getReward() {
        if(count == 0){
            return LONG_CLICK_REWARD;
        }else{
            return LONG_CLICK_REWARD/10/count;
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
    /**
     * Get Command String that can be parsed by parser to rerun
     * @return
     */
    public String toCommand(){
        String at =   "@" + startX +"," + startY
                +"x" + endX +"," + endY;

        return ActionType.LONG_CLICK + " " + at + " " + X + " " + Y;
    }
}
