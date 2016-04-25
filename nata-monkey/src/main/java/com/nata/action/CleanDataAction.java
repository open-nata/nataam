package com.nata.action;

import com.nata.AdbDevice;
import com.nata.AndroidKeyCode;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-25 15:35
 */
public class CleanDataAction extends Action{
    private AdbDevice device = null;
    private String pkgAct;
    public CleanDataAction(AdbDevice device, String pkgAct){
        super(ActionType.CLEAN_DATA);
        this.device = device;
    }

    // set backAction's reward to be less than normal actions;
    @Override
    public double getReward(){
        return 0;
    }


    @Override
    public void fire() {
        device.clearAppData(pkgAct);
        count++;
        device.sleep(5000);
    }

    /**
     * Get Command String that can be parsed by parser to rerun
     * @return
     */
    public String toCommand(){
        return ActionType.CLEAN_DATA + " " + pkgAct;
    }
}
