package com.nata.action;

import com.nata.cmd.AdbDevice;
import com.nata.element.Element;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 16:48
 */
public class TapAction extends Action{
    private AdbDevice device = null;
    private Element element = null;
    public TapAction(AdbDevice device,Element element){
        super.setName(ActionType.TAP);
        this.device = device;
        this.element = element;
    }

    @Override
    public void fire() {
        device.tap(element.getX(),element.getY());
        count++;
    }


    @Override
    public String toString() {
        return "TapAction{" +
                "element=" + element +
                '}';
    }
}
