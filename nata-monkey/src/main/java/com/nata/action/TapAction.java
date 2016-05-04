package com.nata.action;

import com.nata.AdbDevice;
import com.nata.element.Widget;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 16:48
 */
public class TapAction extends Action{
    private AdbDevice device = null;
    private Widget widget = null;
    private int centerX ;
    private int centerY;
    private final double TAP_REWARD = BASE + 0.8;
    public TapAction(AdbDevice device,Widget widget){
        super(ActionType.TAP);
        this.device = device;
        this.widget = widget;
        this.centerX = widget.getCenterX();
        this.centerY = widget.getCenterY();
    }

    public TapAction(AdbDevice device,int centerX , int centerY){
        super(ActionType.TAP);
        this.device = device;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public void fire() {
        device.tap(centerX,centerY);
        count++;
    }

    @Override
    public double getReward() {
        if(count == 0){
            return TAP_REWARD;
        }else{
            return TAP_REWARD/10/count;
        }
    }


    @Override
    public String toString() {
        return super.toString()+"@"+ widget;
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
//        TapAction other = (TapAction) otherObject;
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
        String at =   "@" + widget.getStartX()+"," + widget.getStartY()
                +"x" + widget.getEndX() +"," + widget.getEndY();

        return ActionType.TAP + " "+ at + " " + centerX + " " + centerY;
    }
}
