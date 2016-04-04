package com.nata.monkeys;

import com.nata.action.Action;
import com.nata.action.ActionFactory;
import com.nata.action.BackAction;
import com.nata.action.SwipeAction;
import com.nata.cmd.AdbDevice;
import com.nata.element.Widget;
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
    private Set<String> activitySet = new HashSet<>();
    private List<Action> actionList = new ArrayList<>();
    private Action lastAction = null;
    private Action backAction = null;

    //final variables
    private final int ACTION_COUNTS = 50;
    private final double ALPHA = 0.2;
    private final double GAMA = 0.2;
    private final double PUNISH_OUT_PACKAGE = -10.0;
    private final double PUNISH_SWIPE_NOTWORK= - 2.0;

    List<Action> chosenActions = new ArrayList<>();

    public QLearningMonkey(String pkg, String act, AdbDevice device) {
        super("QLearningMonkey", pkg, act, device);
        actionFactory = new ActionFactory(device);
        restartAction = actionFactory.CreateRestartAction(getPkgAct());
        backAction = actionFactory.createBackAction();
    }

    private Action chooseActionFromState(State curState) {
        Map<Action, Double> actionTable = QMap.get(curState);
        if (actionTable == null || actionTable.size() == 0) {
            System.out.println("Error cannot get actionTable or no action can be choosed");
            return backAction;
        }

        System.out.println("[ActionList] " + actionTable);

        Action chosenAction = null;
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
        System.out.println("charming_max:" + charming_max);
        return chosenActions.get(0);
    }

    private double getRewardFromExperience(State curState, Action action, State nextState) {
        double reward = 0.0;
        // punish to run out of package
        if (!nextState.getAppPackage().equals(getPkg())) {
            reward += PUNISH_OUT_PACKAGE;
        }

        // if the swipe action makes the widget not change then give punish
        if (action instanceof SwipeAction) {
            SwipeAction swipeAction = (SwipeAction) action;
            Widget widget = swipeAction.getWidget();
            Widget widget1 = nextState.getWidgetByResourceId(widget.getResourceId());
            if(widget.equals(widget1) && widget.getText().equals(widget1.getText())){
                reward += PUNISH_SWIPE_NOTWORK;
            }
        }

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
        System.out.println("QLearning Monkey start playing...");
        clearAppData();
        System.out.println("cleaning app data...");

        restartAction.fire();
        actionList.add(restartAction);
        System.out.println("starting app success...");
    }

    /**
     * Add the state to the value map of Q and add the new activity to activitySet
     *
     * @param state
     */
    private void addStateToQMap(State state) {
        //add to activitySet
        if (isInCurrentPkg()) {
            activitySet.add(state.getActivity());
        }
        //add to QMap
        if (QMap.get(state) == null) {
            Map<Action, Double> table = actionFactory.getActionsFromState(state);
            QMap.put(state, table);
            getDevice().screenShot(state.hashCode() + "");
        }
    }

    /**
     * help the monkey to get back to app
     *
     * @return the current state
     */
    private State getBackToApp() {
        while (!isInCurrentPkg()) {
            // if monkey get out of pkg because of back action
            if (lastAction instanceof BackAction) {
                restartAction.fire();
                actionList.add(restartAction);
            } else {
                backAction.fire();
                actionList.add(backAction);
                if (!isInCurrentPkg()) {
                    backAction.fire();
                    actionList.add(backAction);
                }
                lastAction = backAction;
            }
        }
        // get current state
        State state = getCurrentState();
        addStateToQMap(state);
        return state;
    }


    @Override
    public void play() {
        startApp();

        // initial state
        State curState = getCurrentState();
        addStateToQMap(curState);

        // repeat select -> execute -> update
        int cnt = 0;
        while ((++cnt) < ACTION_COUNTS) {
            // if not in pkg, get it back
            if (!isInCurrentPkg()) {
                curState = getBackToApp();
                continue;
            }

            //select action
            Action action = chooseActionFromState(curState);

            //execute
            System.out.println(action);
            action.fire();
            actionList.add(action);
            lastAction = action;

            //update
            State nextState = getCurrentState();
            System.out.println(nextState);
            addStateToQMap(nextState);
            updateValue(curState, action, nextState);

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
        for (String activity : activitySet) {
            System.out.println(activity);
        }

        System.out.println("--------------------[Action report]--------------------");
        int actionCount = actionList.size();
        System.out.println("Actions count: " + actionCount);
        for (Action action : actionList) {
            System.out.println(action);
        }

        System.out.println("--------------------[Widget report]--------------------");
        Set<Widget> widgetSet = getWidgetSet();
        int widgetCount = widgetSet.size();
        System.out.println("Widget count: " + widgetCount);
        for (Widget node : widgetSet) {
            System.out.println(node);
        }

        System.out.println("--------------------[State report]--------------------");
        int stateCount = QMap.size();
        System.out.println("State count: " + stateCount);
        for (State state : QMap.keySet()) {
            System.out.println(state);
        }
    }
}
