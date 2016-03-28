package com.nata.action;

import com.nata.AndroidKeyCode;
import com.nata.cmd.AdbDevice;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-08 22:34
 */
public class BackAction extends Action{
    private AdbDevice device = null;
    public BackAction(AdbDevice device){
        super.setName(ActionType.BACK);
        this.device = device;
    }
    @Override
    public void fire() {
        device.sendKeyEvent(AndroidKeyCode.BACK);
        count++;
    }

    @Override
    public String toString() {
        return super.toString()+"BackAction";
    }
}
