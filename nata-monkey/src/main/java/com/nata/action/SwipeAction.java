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

    public SwipeAction(AdbDevice device,SwipeDirection direction) {
        super.setName(ActionType.SWIPE);
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
    public String toString() {
        return "SwipeAction{" +
                "direction=" + direction +
                '}';
    }
}
