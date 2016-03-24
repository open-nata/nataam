package com.nata.action;

import com.nata.cmd.AdbDevice;
import com.nata.element.Element;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-24 21:23
 */
public class LongClickAction extends Action{

    private AdbDevice device = null;
    private Element element = null;
    public LongClickAction(AdbDevice device,Element element){
        super.setName(ActionType.LONGCLICK);
        this.device = device;
        this.element = element;
    }

    @Override
    public void fire() {
        device.longPress(element.getX(),element.getY());
    }


    @Override
    public String toString() {
        return "LongClickAction{" +
                "element=" + element +
                '}';
    }
}
