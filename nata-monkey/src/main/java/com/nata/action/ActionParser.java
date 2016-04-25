package com.nata.action;

import com.nata.AdbDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-25 15:21
 */
public class ActionParser {

    public static Action parse(String actionString){
        String [] splits = actionString.split(" ",2);
        AdbDevice device = new AdbDevice();
        Action action = null;
       //actionType
        String actionType = splits[0];
        switch (actionType){
            case ActionType.BACK : return new BackAction(device);
            case ActionType.HOME : return new HomeAction(device);
            case ActionType.MENU : return new MenuAction(device);
            case ActionType.CLEAN_DATA : {
                String pkgAct = splits[1];
                return new CleanDataAction(device,pkgAct);
            }
            case ActionType.START_APP : {
                String pkgAct = splits[1];
                return new StartAppAction(device,pkgAct);
            }
            case ActionType.LONG_CLICK : {
                String []xy = splits[1].split(" ");
                int X = Integer.parseInt(xy[0]);
                int Y = Integer.parseInt(xy[1]);
                return new LongClickAction(device,X,Y);
            }
            case ActionType.TAP : {
                String []xy = splits[1].split(" ");
                int X = Integer.parseInt(xy[1]);
                int Y = Integer.parseInt(xy[2]);
                return new TapAction(device,X,Y);
            }
            case ActionType.SWIPE : {
                String []params = splits[1].split(" ");
                SwipeDirection direction = SwipeDirection.valueOf(params[0]);
                int startX = Integer.parseInt(params[1]);
                int startY = Integer.parseInt(params[2]);
                int endX = Integer.parseInt(params[3]);
                int endY = Integer.parseInt(params[4]);
                return  new SwipeAction(device,direction,startX,startY,endX,endY);
            }
            case ActionType.INPUT : {
                String []params = splits[1].split(" ",3);
                int X = Integer.parseInt(params[0]);
                int Y = Integer.parseInt(params[1]);
                String text = params[2];
                return new TextInputAction(device,X,Y,text);
            }
        }
        return action;
    }

    public static List<Action> parse(List<String> actions){
        List<Action> actionList = new ArrayList<>();
        for (String action: actions) {
           actionList.add(parse(action));
        }
        return actionList;
    }
}
