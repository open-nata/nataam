package com.nata.action;

import com.nata.cmd.AdbDevice;
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
    private final double TEXT_INPUT_REWARD = BASE + 0.5;

    public TextInputAction(AdbDevice device,Widget widget){
        super(ActionType.INPUT);
        this.device = device;
        this.widget = widget;
        this.text = TextValueDictionary.getRandomValidValue();
    }

    public Widget getWidget(){
        return widget;
    }

    public void setText(String text){
        this.text = text;
    }

    @Override
    public void fire() {
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
            return 1.0/count;
        }
    }

    @Override
    public String toString() {
        return super.toString()+" @"+widget + " :" + text;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + widget.hashCode();
        hash = 31 * hash + text.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null) {
            return false;
        }

        if (getClass() != otherObject.getClass()) {
            return false;
        }

        TextInputAction other = (TextInputAction) otherObject;

        if(!widget.equals(other.widget)){
            return false;
        }

        if(!text.equals(other.text)){
            return false;
        }

        return true;
    }

}
