package com.nata.monkeys;

import com.nata.action.Action;
import com.nata.action.ActionFactory;
import com.nata.action.BackAction;
import com.nata.cmd.AdbDevice;
import com.nata.state.State;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-18 17:30
 */
public class QLearningMonkey extends AbstractMonkey {
    private Action restartAction = null;
    private ActionFactory actionFactory = null;

    // <state,action,value>
    private Map<State, Map<Action, Double>> QMap = new HashMap<>();
    private Set<String> activitySet =  new HashSet<>();
    private Action lastAction = null;
    private Action backAction = null;

    //final variables
    private final int ACTION_COUNTS = 1000;
    private final double GAMA= 0.7;
    private final double REWARD_OUT_PACKAGE = -10;

    public QLearningMonkey(String pkg, String act, AdbDevice device) {
        super("randomMonkey", pkg, act, device);
        actionFactory = new ActionFactory(device);
        restartAction = actionFactory.CreateRestartAction(getPkgAct());
        backAction = actionFactory.createBackAction();
    }

    private Action chooseActionFromTable(Map<Action, Double> actionTable) {
        Action choosedAction = null;
        double charming_max = 0.0;
        for (Map.Entry<Action, Double> entry : actionTable.entrySet()) {
            double value = entry.getValue();
            if (value > charming_max) {
                choosedAction = entry.getKey();
                charming_max = value;
            }
        }
        return choosedAction;
    }

    private double getRewardFromExperience(State curState,Action action, State nextState){
        double reward = 0.0;
        // not encourage to run out of package
        if(!nextState.getAppPackage().equals(getPkg())){
             reward += REWARD_OUT_PACKAGE;
        }
        reward += action.getReward();
        return reward;
    }


    private double getMaxValueOfState(State nextState){
        Map<Action, Double> actionTable = QMap.get(nextState);
        double reward = 0.0;
        for (Double value: actionTable.values()) {
           if(value > reward) {
               reward = value;
           }
        }
        return reward;
    }


    private void updateValue(State curState,Action action, State nextState){
        Map<Action, Double> actionTable = QMap.get(curState);
        double reward = getRewardFromExperience(curState,action,nextState);
        double future_reward = getMaxValueOfState(nextState);
        actionTable.put(action, reward + GAMA * future_reward);
    }

    @Override
    public void play() {
        System.out.println("QLearning Monkey start playing...");
        restartAction.fire();

        // initial state
        State curState = getCurrentState();
        System.out.println(curState);
        activitySet.add(curState.getActivity());

        if (QMap.get(curState) == null) {
            Map<Action, Double> table= actionFactory.getActionsFromState(curState);
            QMap.put(curState, table);
        }
        // repeat select -> execute -> update
        int cnt = 0;
        while ((++cnt) < ACTION_COUNTS) {
            Action action;
            //select
            if(!isInCurrentPkg()) {
                // if monkey get out of pkg because of back action
                if (lastAction instanceof BackAction) {
                    action = restartAction;
                } else {
                    action = backAction;
                }
            }else{
                Map<Action, Double> actionTable = QMap.get(curState);
                action = chooseActionFromTable(actionTable);
            }

            //execute
            System.out.println(action);
            action.fire();

            //update
            State nextState = getCurrentState();
            System.out.println(nextState);

            if(isInCurrentPkg()){
                activitySet.add(nextState.getActivity());
            }

            if (QMap.get(nextState) == null) {
                Map<Action, Double> table = actionFactory.getActionsFromState(nextState);
                QMap.put(curState, table);
            }
            updateValue(curState,action,nextState);
            curState = nextState;
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void report() {
        //report the activities reached
        System.out.println("activities reached: ");
        for (String activity: activitySet) {
           System.out.println(activity);
        }
    }
}
