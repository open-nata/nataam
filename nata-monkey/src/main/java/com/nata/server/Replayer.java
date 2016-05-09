package com.nata.server;

import com.nata.action.Action;
import com.nata.action.ActionParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-25 17:24
 */
public class Replayer {

    public static  boolean playAction(String actionString){
        Action action = ActionParser.parse(actionString);
        if(action != null){
            action.fire();
            return true;
        }else {
            return false;
        }
    }

    public static  boolean playActions(String actionsString){
        String []actions = actionsString.split("\n");
        for (String action: actions) {
            if(!playAction(action))
                return false;
        }
        return true;
    }

}
