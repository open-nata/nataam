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
    public MenuAction(AdbDevice device){
        super.setName(ActionType.MENU);
        this.device = device;
    }
    @Override
    public void fire() {
        device.sendKeyEvent(AndroidKeyCode.MENU);
    }

    @Override
    public String toString() {
        return "MenuAction";
    }
}