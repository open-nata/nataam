package com.nata.action;

import com.nata.cmd.AdbDevice;
import com.nata.dictionary.TextValueDictionary;
import com.nata.element.Element;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-08 23:43
 */
public class TextInputAction extends Action{
    private AdbDevice device = null;
    private Element element = null;
    private String text = null;
    public TextInputAction(AdbDevice device,Element element){
        super.setName(ActionType.INPUT);
        this.device = device;
        this.element = element;
        this.text = TextValueDictionary.getRandomValidValue();
    }

    @Override
    public void fire() {
        device.tap(element.getX(),element.getY());
        device.sendText(text);
        device.hideSoftKeyBoard();
        count++;
    }

    @Override
    public String toString() {
        return "TextInputAction{" +
                "element=" + element +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + element.hashCode();
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

        if(!element.equals(other.element)){
            return false;
        }

        if(!text.equals(other.text)){
            return false;
        }

        return true;
    }

}
