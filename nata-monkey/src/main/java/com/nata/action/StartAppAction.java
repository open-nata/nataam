package com.nata.action;

import com.nata.cmd.AdbDevice;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-13 17:04
 */
public class StartAppAction extends Action{
    private AdbDevice device = null;
    private String pkgAct = null;
    public StartAppAction(AdbDevice device, String pkgAct){
        super.setName(ActionType.STARTAPP);
        this.device = device;
        this.pkgAct = pkgAct;
    }
    @Override
    public void fire() {
        device.startActivity(pkgAct);
        device.sleep(2000);
    }
}
