package com.nata.monkeys;

import com.nata.action.Action;
import com.nata.action.ActionFactory;
import com.nata.action.BackAction;
import com.nata.action.SwipeAction;
import com.nata.cmd.AdbDevice;
import com.nata.element.UINode;
import com.nata.state.State;

import java.util.*;

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
    private List<Action> actionList = new ArrayList<>();
    private Action lastAction = null;
    private Action backAction = null;

    //final variables
    private final int ACTION_COUNTS = 50;
    private final double GAMA= 0.7;
    private final double PUNISH_OUT_PACKAGE = -10.0;
    private final double PUNISH_SWIPE_NOTWORK= - 0.5;


    public QLearningMonkey(String pkg, String act, AdbDevice device) {
        super("QLearningMonkey", pkg, act, device);
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
        // punish to run out of package
        if(!nextState.getAppPackage().equals(getPkg())){
             reward += PUNISH_OUT_PACKAGE;
        }

        // if the swipe action makes the state not change then give punish
        if(action instanceof SwipeAction && curState.equals(nextState)){
             reward += PUNISH_SWIPE_NOTWORK;
        }

        // the action reward
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
        clearAppData();
        System.out.println("cleaning app data...");
        restartAction.fire();
        actionList.add(restartAction);
        System.out.println("starting app success...");

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
                System.out.println("[ActionList] " +actionTable);
                action = chooseActionFromTable(actionTable);
            }

            //check if monkey can use his knowledge and change the action
            brainStorm(action);

            //execute
            System.out.println(action);
            action.fire();
            actionList.add(action);
            lastAction = action;

            //update
            State nextState = getCurrentState();
            System.out.println(nextState);

            //add to activitySet
            if(isInCurrentPkg()){
                activitySet.add(nextState.getActivity());
            }

            if (QMap.get(nextState) == null) {
                Map<Action, Double> table = actionFactory.getActionsFromState(nextState);
                QMap.put(nextState, table);
            }
            updateValue(curState,action,nextState);
            curState = nextState;

            System.out.println("---------------------------[Experience]---------------------------");
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void report() {
        //report the activities reached
        System.out.println("--------------------[Activities report]--------------------");
        for (String activity: activitySet) {
           System.out.println(activity);
        }

        System.out.println("--------------------[Action report]--------------------");
        int actionCount = actionList.size();
        System.out.println("Actions count: "+actionCount);
        for (Action action: actionList) {
            System.out.println(action);
        }

        System.out.println("--------------------[Widget report]--------------------");
        Set<UINode> widgetSet = getWidgetSet();
        int widgetCount= widgetSet.size();
        System.out.println("Widget count: "+widgetCount);
        for (UINode node: widgetSet) {
            System.out.println(node);
        }

        System.out.println("--------------------[State report]--------------------");
        int stateCount = QMap.size();
        System.out.println("State count: "+stateCount);
        for (State state: QMap.keySet()) {
            System.out.println(state);
        }
    }
}
