package com.nata.action;

import com.nata.cmd.AdbDevice;
import com.nata.element.Element;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 21:04
 */
public class SwipeAction extends Action{
    private AdbDevice device;
    private Element element = null;
    public SwipeAction(Element element, AdbDevice device){
        super.setName(ActionType.SWIPE);
        this.device = device;
        this.element = element;
    }

    @Override
    public void fire() {

    }
}
