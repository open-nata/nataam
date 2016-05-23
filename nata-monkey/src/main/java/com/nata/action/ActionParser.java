package com.nata.action;

import com.nata.AdbDevice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
                String pkg = splits[1];
                action = new CleanDataAction(device,pkg);
                break;
            }
            case ActionType.START_APP : {
                String pkgAct = splits[1];
                action = new StartAppAction(device,pkgAct);
                break;
            }
            case ActionType.LONG_CLICK : {
                String []params = splits[1].split(" ",3);
                String at = params[0];
                String []coordinates = at.substring(1).split("[,x]");
                int startX = Integer.parseInt(coordinates[0]);
                int startY = Integer.parseInt(coordinates[1]);
                int endX = Integer.parseInt(coordinates[2]);
                int endY = Integer.parseInt(coordinates[3]);

                int X = Integer.parseInt(params[1]);
                int Y = Integer.parseInt(params[2]);
                action = new LongClickAction(device,startX,startY,endX,endY,X,Y);
                break;
            }
            case ActionType.TAP : {
                String []params = splits[1].split(" ",3);
                String at = params[0];
                String []coordinates = at.substring(1).split("[,x]");
                int startX = Integer.parseInt(coordinates[0]);
                int startY = Integer.parseInt(coordinates[1]);
                int endX = Integer.parseInt(coordinates[2]);
                int endY = Integer.parseInt(coordinates[3]);

                int X = Integer.parseInt(params[1]);
                int Y = Integer.parseInt(params[2]);

                action = new TapAction(device,startX,startY,endX,endY,X,Y);
                break;
            }
            case ActionType.SWIPE : {
                String []params = splits[1].split(" ",2);

                String at = params[0];
                String []coordinates = at.substring(1).split("[,x]");
                int startX = Integer.parseInt(coordinates[0]);
                int startY = Integer.parseInt(coordinates[1]);
                int endX = Integer.parseInt(coordinates[2]);
                int endY = Integer.parseInt(coordinates[3]);

                SwipeDirection direction = SwipeDirection.valueOf(params[1]);
                action = new SwipeAction(device,startX,startY,endX,endY,direction);
                break;
            }
            case ActionType.INPUT : {
                String []params = splits[1].split(" ",4);
                String at = params[0];
                String []coordinates = at.substring(1).split("[,x]");
                int startX = Integer.parseInt(coordinates[0]);
                int startY = Integer.parseInt(coordinates[1]);
                int endX = Integer.parseInt(coordinates[2]);
                int endY = Integer.parseInt(coordinates[3]);

                int X = Integer.parseInt(params[1]);
                int Y = Integer.parseInt(params[2]);

                String text = params[3];
                action = new TextInputAction(device,startX,startY,endX,endY,X,Y,text);
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

//        File actionFile = new File("scripts/fcws_dfs.txt");
//
//        List<Action> actionList= ActionParser.parse(actionFile);
//        for (Action action: actionList) {
//            action.fire();
//        }
//        String  at= "172,234x279,1931" ;
//        System.out.println(at.substring(1,at.length()-1));
//        String [] cordinates = at.split("[,x]");
//        System.out.println(Arrays.toString(cordinates));

        String str ="Swipe @0,75x1080,1776 LEFT";
        Action action = ActionParser.parse(str);
        action.fire();
    }
}
