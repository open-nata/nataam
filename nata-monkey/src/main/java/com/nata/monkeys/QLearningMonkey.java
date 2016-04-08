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
    // <state,action,value>
    private Map<State, Map<Action, Double>> QMap = new HashMap<>();

    //final variables
    private final int ACTION_COUNTS = 1000;
    private final int Frequency = 100;
    private final double ALPHA = 0.2;
    private final double GAMA = 0.3;
    private final double PUNISH_OUT_PACKAGE = -10.0;
    private final double PUNISH_SWIPE_NOTWORK= - 2.0;

    List<Action> chosenActions = new ArrayList<>();

    public QLearningMonkey(String pkg, String act, AdbDevice device) {
        super("QLearningMonkey", pkg, act, device);
    }

    private Action chooseActionFromState(State curState) {
        Map<Action, Double> actionTable = QMap.get(curState);
        if (actionTable == null || actionTable.size() == 0) {
            System.out.println("Error cannot get actionTable or no action can be choosed");
            return getBackAction();
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

    /**
     * Add the current state to the value map of Q
     * @return current state
     */
    @Override
    public State getCurrentState() {
        State state  = super.getCurrentState();

        if (QMap.get(state) == null) {
            Map<Action, Double> table = getActionFactory().getActionsFromState(state);
            LogUtil.debug("ActionTable : " + table.toString());
            QMap.put(state, table);
            getDevice().screenShot(state.hashCode() + "");
        }

        return state;
    }

    @Override
    public void play() {
        startApp();
        State curState = getCurrentState();

        // repeat select -> execute -> update
        int cnt = 0;
        while ((++cnt) < ACTION_COUNTS) {
            if (!isInCurrentPkg()) {
                getBackToApp();
                curState =  getCurrentState();
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

            if(cnt % Frequency== 0){
                summary(cnt/Frequency);
            }
        }
    }

    @Override
    public void report() {
        super.report();

        LogUtil.debug("--------------------[State report]--------------------");
        int stateCount = QMap.size();
        LogUtil.debug("State count: " + stateCount);
        for (State state : QMap.keySet()) {
            LogUtil.debug(state.toString());
        }
    }

    @Override
    public void summary(int tick){
        String info ="States count " + QMap.size() + " | ";
        info += "Activity count " + getActivitySet().size() + " | ";
        info += "Widget count " + getWidgetSet().size() + " | ";
        LogUtil.info("Tick  "+ tick + " : " + info);
    }

}
