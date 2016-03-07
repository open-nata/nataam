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

    public Element(int x,int y){
        this.X = x;
        this.Y = y;
    }

    public Element(String bounds){
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

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }
}
