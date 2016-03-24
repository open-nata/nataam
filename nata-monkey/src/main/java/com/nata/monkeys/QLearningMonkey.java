package com.nata.monkeys;

import com.nata.action.Action;
import com.nata.action.ActionFactory;
import com.nata.cmd.AdbDevice;
import com.nata.state.State;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-18 17:30
 */
public class QLearningMonkey extends AbstractMonkey{
    private Action restartAction = null;
    private Action backAction = null;
    private Action menuAction = null;
    private ActionFactory actionFactory = null;


    private Map<State,Map<Action,Double>> QMap = new HashMap<>();


    private final int ACTION_COUNTS = 1000;

    public QLearningMonkey(String pkg, String act, AdbDevice device){
        super("randomMonkey", pkg, act, device);
        actionFactory = new ActionFactory(device);
        restartAction = actionFactory.CreateRestartAction(getPkgAct());
        backAction = actionFactory.getBackAction();
        menuAction = actionFactory.getMenuAction();
    }

    @Override
    public void play() {
        System.out.println("Random Monkey start playing...");
        restartAction.fire();
        State curState = getCurrentState();
        if(QMap.get(curState) == null){
            Map<Action,Double> actionTable = actionFactory.getActionsFromState(curState);
        }

        int cnt  = 0;
        while((++cnt) < ACTION_COUNTS){

        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void report() {

    }
}
