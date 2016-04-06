package com.nata.monkeys;

import com.nata.action.*;
import com.nata.cmd.AdbDevice;
import com.nata.element.Widget;
import com.nata.state.State;
import com.nata.utils.LogUtil;

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
    private Set<String> activitySet = new HashSet<>();
    private List<Action> actionList = new ArrayList<>();
    private Action lastAction = null;
    private Action backAction = null;
    private Action homeAction = null;

    //final variables
    private final int ACTION_COUNTS = 200;
    private final double ALPHA = 0.2;
    private final double GAMA = 0.3;
    private final double PUNISH_OUT_PACKAGE = -10.0;
    private final double PUNISH_SWIPE_NOTWORK= - 2.0;

    List<Action> chosenActions = new ArrayList<>();

    public QLearningMonkey(String pkg, String act, AdbDevice device) {
        super("QLearningMonkey", pkg, act, device);
        actionFactory = new ActionFactory(device);
        restartAction = actionFactory.CreateRestartAction(getPkgAct());
        backAction = actionFactory.createBackAction();
        homeAction = actionFactory.createHomeAction();
    }

    private Action chooseActionFromState(State curState) {
        Map<Action, Double> actionTable = QMap.get(curState);
        if (actionTable == null || actionTable.size() == 0) {
            System.out.println("Error cannot get actionTable or no action can be choosed");
            return backAction;
        }

        Action chosenAction;
        double charming_max = 0.0;

        for (Map.Entry<Action, Double> entry : actionTable.entrySet()) {
            double value = entry.getValue();
            if (value > charming_max) {
                chosenAction = entry.getKey();
                charming_max = value;
                chosenActions.clear();
                chosenActions.add(chosenAction);
            } else if (Math.abs(value - charming_max) < 0.0001) { //almost equal
                chosenAction = entry.getKey();
                chosenActions.add(chosenAction);
            }
        }
        Collections.shuffle(chosenActions);
        return chosenActions.get(0);
    }

    private double getRewardFromExperience(State curState, Action action, State nextState) {
        double reward = 0.0;

        if (!nextState.getAppPackage().equals(getPkg())) {
            reward += PUNISH_OUT_PACKAGE;
        }

        // if the swipe action makes the widget not change then give punish
//        if (action instanceof SwipeAction) {
//            SwipeAction swipeAction = (SwipeAction) action;
//            Widget widget = swipeAction.getWidget();
//            Widget widget1 = nextState.getWidgetByResourceId(widget.getResourceId());
//            if(widget.equals(widget1) && widget.getText().equals(widget1.getText())){
//                reward += PUNISH_SWIPE_NOTWORK;
//            }
//        }

        // the action reward
        reward += action.getReward();
        return reward;
    }


    private double getMaxValueOfState(State nextState) {
        Map<Action, Double> actionTable = QMap.get(nextState);
        double maxValue = 0.0;
        for (Double value : actionTable.values()) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }


    private void updateValue(State curState, Action action, State nextState) {
        Map<Action, Double> actionTable = QMap.get(curState);
        double reward = getRewardFromExperience(curState, action, nextState);
        double future_reward = getMaxValueOfState(nextState);
        actionTable.put(action, reward + GAMA * future_reward);
    }


    private void startApp() {
        LogUtil.info("QLearning Monkey start playing...");
        clearAppData();
        LogUtil.info("App data cleaned!");
        restartAction.fire();
        actionList.add(restartAction);
        LogUtil.info("Starting app success!");
    }

    /**
     * Add the current state to the value map of Q and add the new activity to activitySet
     * @return current state
     */
    @Override
    public State getCurrentState() {
        State state  = super.getCurrentState();

        if (isInCurrentPkg()) {
            activitySet.add(state.getActivity());
        }

        if (QMap.get(state) == null) {
            Map<Action, Double> table = actionFactory.getActionsFromState(state);
            LogUtil.debug("ActionTable : " + table.toString());
            QMap.put(state, table);
            getDevice().screenShot(state.hashCode() + "");
        }

        return state;
    }

    /**
     * help the monkey to get back to app
     *
     * @return the current state
     */
    private State getBackToApp() {
        boolean forceQuit = false;
        while (!isInCurrentPkg()) {
            // if even the restart action cannot restart it ;
            if(forceQuit){
                clearAppData();
                executeAction(restartAction);
            }
            else if(lastAction instanceof StartAppAction){
                executeAction(homeAction);
                executeAction(restartAction);
                forceQuit = true;
            }
            // if monkey get out of pkg because of back action
            else if (lastAction instanceof BackAction) {
                executeAction(restartAction);
            } else {
                executeAction(backAction);
                if (!isInCurrentPkg()) {
                    executeAction(backAction);
                }
            }
        }

        return getCurrentState();
    }

    /**
     * Fire the action and add the action to actionList
     * @param action to be executed
     */
    private void executeAction(Action action){
        action.fire();
        actionList.add(action);
        lastAction = action;
    }

    @Override
    public void play() {
        startApp();
        State curState = getCurrentState();

        // repeat select -> execute -> update
        int cnt = 0;
        while ((++cnt) < ACTION_COUNTS) {
            if (!isInCurrentPkg()) {
                curState = getBackToApp();
                continue;
            }

            // select
            Action chosenAction = chooseActionFromState(curState);
            LogUtil.debug(chosenAction.toString());

            // execute
            executeAction(chosenAction);

            State nextState = getCurrentState();

            //update Value
            updateValue(curState, chosenAction, nextState);

            curState = nextState;
        }
    }

    @Override
    public void stop() {

   }

    @Override
    public void report() {
        //report the activities reached
        System.out.println("--------------------[Activities report]--------------------");
        for (String activity : activitySet) {
            System.out.println(activity);
        }

//        System.out.println("--------------------[Action report]--------------------");
//        int actionCount = actionList.size();
//        System.out.println("Actions count: " + actionCount);
//        for (Action action : actionList) {
//            System.out.println(action);
//        }

        System.out.println("--------------------[Widget report]--------------------");
        Set<Widget> widgetSet = getWidgetSet();
        int widgetCount = widgetSet.size();
        System.out.println("Widget count: " + widgetCount);
        for (Widget node : widgetSet) {
            System.out.println(node);
        }

//        System.out.println("--------------------[State report]--------------------");
//        int stateCount = QMap.size();
//        System.out.println("State count: " + stateCount);
//        for (State state : QMap.keySet()) {
//            System.out.println(state);
//        }
    }

}
