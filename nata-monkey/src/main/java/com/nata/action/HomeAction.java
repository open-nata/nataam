package com.nata.action;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-04 20:13
 */

import com.nata.AndroidKeyCode;
import com.nata.AdbDevice;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-08 23:26
 */
public class HomeAction extends Action{
    private AdbDevice device = null;

    public HomeAction(AdbDevice device){
        super(ActionType.HOME);
        this.device = device;
    }
    @Override
    public void fire() {
        device.sendKeyEvent(AndroidKeyCode.HOME);
        count++;
    }

    @Override
    public double getReward() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString()+"HomeAction";
    }

    /**
     * Get Command String that can be parsed by parser to rerun
     * @return
     */
    public String toCommand(){
        return ActionType.HOME;
    }
}
