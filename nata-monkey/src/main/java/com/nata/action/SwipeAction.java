package com.nata.action;

import com.nata.cmd.AdbDevice;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 21:04
 */
public class SwipeAction extends Action {
    private AdbDevice device;
    private SwipeDirection direction;
    private final double SWIPE_REWARD = BASE + 0.3;

    public SwipeAction(AdbDevice device,SwipeDirection direction) {
        super(ActionType.SWIPE);
        this.device = device;
        this.direction = direction;
    }

    @Override
    public void fire() {
        switch (direction){
            case DOWN: device.swipeToDown();break;
            case UP: device.swipeToUp();break;
            case LEFT:device.swipeToLeft();break;
            case RIGHT:device.swipeToRight();break;
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

    @Override
    public int hashCode() {
        int hash = 17;
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

        if(!direction.equals(other.direction)){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
