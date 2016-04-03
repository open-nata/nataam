package com.nata.action;

import com.nata.AndroidKeyCode;
import com.nata.cmd.AdbDevice;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-08 23:26
 */
public class MenuAction extends Action{
    private AdbDevice device = null;
    private final double MENU_REWARD = BASE - 0.1;


    public MenuAction(AdbDevice device){
        super(ActionType.MENU);
        this.device = device;
    }
    @Override
    public void fire() {
        device.sendKeyEvent(AndroidKeyCode.MENU);
        count++;
    }

    @Override
    public double getReward() {
        if(count == 0){
            return MENU_REWARD;
        }else{
            return 1.0/count;
        }
    }

    @Override
    public String toString() {
        return super.toString()+"MenuAction";
    }
}