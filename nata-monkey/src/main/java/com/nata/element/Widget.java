package com.nata.element;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-26 20:51
 */
public class Widget {
    /**
     * The attributes that can be achieved from dumpfile
     */
    private String text;        // state?
    private String resourceId;  // state
    private String className;   // state
    private String contentDesc;
    private String checkable;
    private String checked;     // state
    private String clickable;
    private String bounds;
    private String packageName;
    private String enabled;     // state
    private String focusable;
    private String focused;
    private String scrollable;
    private String long_clickable;
    private String password;
    private String selected;    //state

    /**
     * The properties that can be extracted from attributes already existed
     */
    // from bounds
    private int startX = 0 ;
    private int startY = 0 ;
    private int endX = 0;
    private int endY = 0;


    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getX(){
        return startX+1;
    }

    public int getY(){
        return startY+1;
    }

    public int getCenterX(){
       return  (endX - startX) / 2 + startX;
    }

    public int getCenterY(){
        return (endY - startY) / 2 + startY;
    }

    public String getFocused() {
        return focused;
    }

    public void setFocused(String focused) {
        this.focused = focused;
    }

    public String getScrollable() {
        return scrollable;
    }

    public void setScrollable(String scrollable) {
        this.scrollable = scrollable;
    }

    public String getLong_clickable() {
        return long_clickable;
    }

    public void setLong_clickable(String long_clickable) {
        this.long_clickable = long_clickable;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public void setFocusable(String focusable) {
        this.focusable = focusable;
    }

    public String getFocusable() {
        return focusable;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getBounds() {
        return bounds;
    }

    // if the widget don't has resource id we won't calculate its leftX,leftY,rightX,rightY
    public void setBounds(String bounds) {
        this.bounds = bounds;
        if(!resourceId.equals("")){
            Pattern pattern = Pattern.compile("([0-9]+)");
            Matcher mat = pattern.matcher(bounds);
            ArrayList<Integer> coords = new ArrayList<>();
            while (mat.find()) {
                coords.add(new Integer(mat.group()));
            }

            startX = coords.get(0);
            startY = coords.get(1);
            endX = coords.get(2);
            endY = coords.get(3);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public String getCheckable() {
        return checkable;
    }

    public void setCheckable(String checkable) {
        this.checkable = checkable;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getClickable() {
        return clickable;
    }

    public void setClickable(String clickable) {
        this.clickable = clickable;
    }

    @Override
    public String toString() {
        return "Widget{" +
                "text='" + text + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", className='" + className + '\'' +
                ", contentDesc='" + contentDesc + '\'' +
                ", checkable='" + checkable + '\'' +
                ", checked='" + checked + '\'' +
                ", clickable='" + clickable + '\'' +
                ", bounds='" + bounds + '\'' +
                ", packageName='" + packageName + '\'' +
                ", enabled='" + enabled + '\'' +
                ", focusable='" + focusable + '\'' +
                ", focused='" + focused + '\'' +
                ", scrollable='" + scrollable + '\'' +
                ", long_clickable='" + long_clickable + '\'' +
                ", password='" + password + '\'' +
                ", selected='" + selected + '\'' +
                '}';
    }



//    @Override
//    public String toString() {
//        String description = className + " " + resourceId +" " + text;
//        return "Widget{" +
//                "X=" + getX() +
//                ", Y=" + getY() +
//                ", description='" + description + '\'' +
//                '}';
//    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + startX;
        hash = 31 * hash + startY;
        hash = 31 * hash + endX;
        hash = 31 * hash + endY;
        hash = 31 * hash + packageName.hashCode();
        hash = 31 * hash + resourceId.hashCode();
        hash = 31 * hash + className.hashCode();
        hash = 31 * hash + enabled.hashCode();
        hash = 31 * hash + checked.hashCode();
        hash = 31 * hash + selected.hashCode();

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

        Widget other = (Widget) otherObject;
        if(startX!=other.startX || startY != other.startY || endX !=other.endX || endY != other.endY){
            return false;
        }

        if(!packageName.equals(other.packageName)){
            return false;
        }
        if(!resourceId.equals(other.resourceId)){
            return false;
        }

        if(!className.equals(other.className)){
            return false;
        }

        if(!enabled.equals(other.enabled)){
            return false;
        }

        if(!checked.equals(other.checked)){
            return false;
        }

        if(!selected.equals(other.selected)){
            return false;
        }

        return true;
    }
}
