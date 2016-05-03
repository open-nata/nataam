package com.nata.server;

import com.nata.action.Action;
import com.nata.action.ActionParser;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-25 17:24
 */
public class Replayer {

    public static  boolean playAction(String actionString){
        Action action = ActionParser.parse(actionString);
        action.fire();
        return true;
    }

}
