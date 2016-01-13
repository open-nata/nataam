package com.nata.strategy;

import com.nata.action.Action;
import com.nata.action.TapAction;
import com.nata.cmd.AdbDevice;

import java.util.ArrayList;


/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 16:31
 */
public class RandomMonkeyStrategy extends Strategy {

    private ArrayList<Action> actionlist = null;

    public RandomMonkeyStrategy(AdbDevice device){
        super(device);
        actionlist = new ArrayList<Action>();
    }

    public Action getNextAction() {
        Action action = new TapAction();
        return action;
    }

    public boolean hasNextAction() {
        return false;
    }
}