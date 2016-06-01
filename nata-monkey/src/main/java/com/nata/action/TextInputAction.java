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
    private int X;
    private int Y;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private final double TEXT_INPUT_REWARD = BASE + 1.0;

    public TextInputAction(AdbDevice device,Widget widget){
        super(ActionType.INPUT);
        this.device = device;
        this.widget = widget;
        this.text = TextValueDictionary.getInstance().getInput(widget);
        this.X = widget.getX();
        this.Y = widget.getY();

        this.startX =  widget.getStartX();
        this.startY = widget.getStartY();
        this.endX = widget.getEndX();
        this.endY = widget.getEndY();
    }

    public Widget getWidget(){
        return widget;
    }

    public TextInputAction(AdbDevice device,int startX, int startY, int endX, int endY,int X,int Y ,String text){
        super(ActionType.INPUT);
        this.device = device;
        this.text = text;
        this.X = X;
        this.Y = Y;

        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void fire() {
        device.longPress(X, Y);
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
    /**
     * Get Command String that can be parsed by parser to rerun
     * @return
     */
    public String toCommand(){
        String at =   "@" + this.startX +"," + this.startY
                +"x" + this.endX +"," + this.endY;

        return ActionType.INPUT+ " " + at +" " + X + " " + Y + " "+ text;


    }
}
