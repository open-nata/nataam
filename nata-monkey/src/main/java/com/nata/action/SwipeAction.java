package com.nata.action;

import com.nata.AdbDevice;
import com.nata.element.Widget;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 21:04
 */
public class SwipeAction extends Action {
    private AdbDevice device;
    private SwipeDirection direction;
    private Widget widget;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private final double SWIPE_REWARD = BASE + 0.6;

    public SwipeAction(AdbDevice device, Widget widget,SwipeDirection direction) {
        super(ActionType.SWIPE);
        this.device = device;
        this.widget = widget;
        this.direction = direction;

        startX = widget.getStartX();
        startY = widget.getStartY();
        endX = widget.getEndX();
        endY = widget.getEndY();
    }

    public SwipeAction(AdbDevice device,int startX, int startY, int endX, int endY,SwipeDirection direction ) {
        super(ActionType.SWIPE);
        this.device = device;
        this.direction = direction;

        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void fire() {
        switch (direction){
            case DOWN: device.swipeToDown(startX,startY,endX,endY);break;
            case UP: device.swipeToUp(startX,startY,endX,endY);break;
            case LEFT:device.swipeToLeft(startX,startY,endX,endY);break;
            case RIGHT:device.swipeToRight(startX,startY,endX,endY);break;
        }
        count++;
    }

    @Override
    public double getReward() {
        if(count == 0){
            if(direction == SwipeDirection.LEFT || direction == SwipeDirection.DOWN)
               return SWIPE_REWARD + 0.1;
            else
                return SWIPE_REWARD - 0.1;
        }else{
            return SWIPE_REWARD/10/count;
        }
    }

    public Widget getWidget(){
        return widget;
    }

//    @Override
//    public int hashCode() {
//        int hash = 17;
//        hash = 31 * hash + type.hashCode();
//        hash = 31 * hash + direction.hashCode();
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
//        SwipeAction other = (SwipeAction) otherObject;
//        if(!type.equals(other.type)){
//            return false;
//        }
//
//        if(!direction.equals(other.direction)){
//            return false;
//        }
//        return true;
//    }

    @Override
    public String toString() {
        return super.toString()+direction;
    }


    /**
     * Get Command String that can be parsed by parser to rerun
     * @return
     */
    public String toCommand(){
        String at =   "@" + startX +"," + startY
                +"x" + endX +"," + endY;

        return ActionType.SWIPE+ " " + at + " " + direction;
    }
}
