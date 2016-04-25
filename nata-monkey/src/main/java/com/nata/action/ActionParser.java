package com.nata.action;

import com.nata.AdbDevice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            case ActionType.BACK : action = new BackAction(device);break;
            case ActionType.HOME : action = new HomeAction(device);break;
            case ActionType.MENU : action = new MenuAction(device);break;
            case ActionType.CLEAN_DATA : {
                String pkgAct = splits[1];
                action = new CleanDataAction(device,pkgAct);
                break;
            }
            case ActionType.START_APP : {
                String pkgAct = splits[1];
                action = new StartAppAction(device,pkgAct);
                break;
            }
            case ActionType.LONG_CLICK : {
                String []xy = splits[1].split(" ");
                int X = Integer.parseInt(xy[0]);
                int Y = Integer.parseInt(xy[1]);
                action = new LongClickAction(device,X,Y);
                break;
            }
            case ActionType.TAP : {
                String []xy = splits[1].split(" ");
                int X = Integer.parseInt(xy[0]);
                int Y = Integer.parseInt(xy[1]);
                action = new TapAction(device,X,Y);
                break;
            }
            case ActionType.SWIPE : {
                String []params = splits[1].split(" ");
                SwipeDirection direction = SwipeDirection.valueOf(params[0]);
                int startX = Integer.parseInt(params[1]);
                int startY = Integer.parseInt(params[2]);
                int endX = Integer.parseInt(params[3]);
                int endY = Integer.parseInt(params[4]);
                action = new SwipeAction(device,direction,startX,startY,endX,endY);
                break;
            }
            case ActionType.INPUT : {
                String []params = splits[1].split(" ",3);
                int X = Integer.parseInt(params[0]);
                int Y = Integer.parseInt(params[1]);
                String text = params[2];
                action = new TextInputAction(device,X,Y,text);
                break;
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

    public static List<Action> parse(File actionsFile){
        List<Action> actionList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(actionsFile));
            String line;
            while ((line = br.readLine()) != null) {
                actionList.add(parse(line));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return actionList;
    }

    public static void main(String[] args) {
//        List<String> actions = new ArrayList<>();
//        actions.add("StartApp com.cvicse.zhnt/.LoadingActivity");
//        actions.add("Tap 298 1111");
//        actions.add("StartApp com.cvicse.zhnt/.LoadingActivity");
//        actions.add("Tap 788 1111");
//        actions.add("StartApp com.cvicse.zhnt/.LoadingActivity");

        File actionFile = new File("scripts/fcws_dfs.txt");

        List<Action> actionList= ActionParser.parse(actionFile);
        for (Action action: actionList) {
            action.fire();
        }
    }
}
