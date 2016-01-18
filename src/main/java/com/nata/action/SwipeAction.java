package com.nata.action;

import com.nata.element.Element;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 21:04
 */
public class SwipeAction extends Action{

    public SwipeAction(Element element){
        super(element);
        super.setName(ActionType.TAP);
    }

    @Override
    public boolean fire() {
        return false;
    }
}
