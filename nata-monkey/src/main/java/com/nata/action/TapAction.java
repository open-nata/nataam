package com.nata.action;

import com.nata.element.Element;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 16:48
 */
public class TapAction extends Action{

    public TapAction(Element element){
        super(element);
        super.setName(ActionType.TAP);
    }

    @Override
    public boolean fire() {
        return false;
    }
}
