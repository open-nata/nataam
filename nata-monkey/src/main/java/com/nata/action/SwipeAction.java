package com.nata.action;

import com.nata.cmd.AdbDevice;
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
    private final double SWIPE_REWARD = BASE + 0.3;

    public SwipeAction(AdbDevice device, Widget widget,SwipeDirection direction) {
        super(ActionType.SWIPE);
        this.device = device;
        this.widget = widget;
        this.direction = direction;
    }

    @Override
    public void fire() {
        int startX = widget.getStartX();
        int startY = widget.getStartY();
        int endX = widget.getEndX();
        int endY = widget.getEndY();
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
            return SWIPE_REWARD;
        }else{
            return 1.0/count;
        }
    }

    public Widget getWidget(){
        return widget;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + type.hashCode();
        hash = 31 * hash + direction.hashCode();
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

        SwipeAction other = (SwipeAction) otherObject;
        if(!type.equals(other.type)){
            return false;
        }

        if(!direction.equals(other.direction)){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString()+direction;
    }


}
