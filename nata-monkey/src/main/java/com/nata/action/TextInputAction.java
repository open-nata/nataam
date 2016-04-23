package com.nata.action;

import com.nata.AdbDevice;
import com.nata.dictionary.TextValueDictionary;
import com.nata.element.Widget;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-08 23:43
 */
public class TextInputAction extends Action{
    private AdbDevice device = null;
    private Widget widget = null;
    private String text = null;
    private final double TEXT_INPUT_REWARD = BASE + 1.0;

    public TextInputAction(AdbDevice device,Widget widget){
        super(ActionType.INPUT);
        this.device = device;
        this.widget = widget;
    }

    public Widget getWidget(){
        return widget;
    }


    @Override
    public void fire() {
        this.text = TextValueDictionary.getInstance().getInput(widget);
        device.longPress(widget.getX(), widget.getY());
        device.sendText(text);
        device.hideSoftKeyBoard();
        count++;
    }

    @Override
    public double getReward() {
        if(count == 0){
            return TEXT_INPUT_REWARD;
        }else{
            return TEXT_INPUT_REWARD/10/count;
        }
    }

    @Override
    public String toString() {
        return super.toString()+" @"+widget + " :" + text;
    }

//    @Override
//    public int hashCode() {
//        int hash = 17;
//        hash = 31 * hash + type.hashCode();
//        hash = 31 * hash + widget.hashCode();
//        hash = 31 * hash + text.hashCode();
//
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object otherObject) {
//        if (this == otherObject) {
//            return true;
//        }
//
//        if (otherObject == null) {
//            return false;
//        }
//
//        if (getClass() != otherObject.getClass()) {
//            return false;
//        }
//
//        TextInputAction other = (TextInputAction) otherObject;
//
//        if(!type.equals(other.type)){
//            return false;
//        }
//
//        if(!widget.equals(other.widget)){
//            return false;
//        }
//
//        if(!text.equals(other.text)){
//            return false;
//        }
//
//        return true;
//    }

}
