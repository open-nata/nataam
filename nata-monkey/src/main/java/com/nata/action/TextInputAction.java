package com.nata.action;

import com.nata.cmd.AdbDevice;
import com.nata.element.Element;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-08 23:43
 */
public class TextInputAction extends Action{
    private AdbDevice device = null;
    private Element element = null;
    public TextInputAction(Element element,AdbDevice device){
        super.setName(ActionType.TAP);
        this.device = device;
        this.element = element;
    }

    @Override
    public void fire() {
        device.tap(element.getX(),element.getY());
        device.sendText("random text");
    }


    @Override
    public String toString() {
        return "TextInputAction{" +
                "element=" + element +
                '}';
    }
}
