package com.nata.action;

import com.nata.AndroidKeyCode;
import com.nata.AdbDevice;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-08 23:26
 */
public class MenuAction extends Action{
    private AdbDevice device = null;
    private final double MENU_REWARD = BASE - 0.3;


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
            return MENU_REWARD/10/count;
        }
    }

    @Override
    public String toString() {
        return super.toString()+"MenuAction";
    }

    /**
     * Get Command String that can be parsed by parser to rerun
     * @return
     */
    public String toCommand(){
        return ActionType.MENU;
    }
}