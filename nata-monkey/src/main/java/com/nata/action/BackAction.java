package com.nata.action;

import com.nata.AndroidKeyCode;
import com.nata.AdbDevice;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-08 22:34
 */
public class BackAction extends Action{
    private AdbDevice device = null;
    private final double BACK__REWARD = BASE - 0.5;
    public BackAction(AdbDevice device){
        super(ActionType.BACK);
        this.device = device;
    }

    // set backAction's reward to be less than normal actions;
    @Override
    public double getReward(){
        if(count == 0){
            return BACK__REWARD;
        }else{
            return BACK__REWARD/10/count;
        }
    }


    @Override
    public void fire() {
        device.sendKeyEvent(AndroidKeyCode.BACK);
        count++;
        device.sleep(1000);
    }
}
