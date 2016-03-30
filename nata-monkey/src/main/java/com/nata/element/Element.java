package com.nata.element;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 16:46
 */
public class Element {
//    private Activity activity;
    private int X;
    private int Y;
    private String className;
    private String resourceId;
    private String text;

    public Element(UINode node){
        this.className = node.getClassName();
        this.resourceId = node.getResourceId();
        this.text = node.getText();

        String bounds = node.getBounds();
        Pattern pattern = Pattern.compile("([0-9]+)");
        Matcher mat = pattern.matcher(bounds);
        ArrayList<Integer> coords = new ArrayList<Integer>();
        while (mat.find()) {
            coords.add(new Integer(mat.group()));
        }

        int startX = coords.get(0);
        int startY = coords.get(1);
        int endX = coords.get(2);
        int endY = coords.get(3);

        int centerCoordsX = (endX - startX) / 2 + startX;
        int centerCoordsY = (endY - startY) / 2 + startY;
        this.X = centerCoordsX;
        this.Y = centerCoordsY;
    }

    @Override
    public String toString() {
        String description = className + " " + resourceId +" " + text;
        return "Element{" +
                "X=" + X +
                ", Y=" + Y +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + X;
        hash = 31 * hash + Y;
        hash = 31 * hash + className.hashCode();
        hash = 31 * hash + resourceId.hashCode();
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

        Element other = (Element) otherObject;
        if(X!= other.X){
            return false;
        }
        if(Y!= other.Y){
            return false;
        }
        if(!resourceId.equals(other.resourceId)){
            return false;
        }
        if(!className.equals(other.className)){
            return false;
        }
        if(!text.equals(other.text)){
            return false;
        }

        return true;
    }

    public String getClassName() {
        return className;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getText() {
        return text;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
