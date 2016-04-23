package com.nata.monkeys;

import com.nata.action.*;
import com.nata.AdbDevice;
import com.nata.state.State;

import java.util.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-18 18:49
 */
public class RandomMonkey extends AbstractMonkey {
    private Set<String> activitySet = new HashSet<>();

    public RandomMonkey(int actionCount,String pkg, String act, AdbDevice device) {
        super("randomMonkey",actionCount, pkg, act, device);
    }

    public RandomMonkey(String pkg, String act, AdbDevice device) {
        this(1000,pkg,act,device);
    }



    private Action chooseActionFromState(State curState) {
        Map<Action, Double> actionTable = curState.getActionTable();
        if (actionTable == null || actionTable.size() == 0) {
            System.out.println("Error cannot get actionTable or no action can be choosed");
            return getBackAction();
        }

        Set<Action> actionSet = actionTable.keySet();
        List<Action> list = new ArrayList<>();
        list.addAll(actionSet);
        Collections.shuffle(list);
        return list.get(0);
    }

    @Override
    public void play() {
        startApp();

        State curState = getCurrentState();
        if (isInCurrentPkg()) {
            activitySet.add(curState.getActivity());
        }

        while (cnt <= ACTION_COUNTS) {
            // if not in pkg, get it back
            if (!isInCurrentPkg()) {
                getBackToApp();
                curState = getCurrentState();
                continue;
            }

            Action action = chooseActionFromState(curState);

            action.fire();

            curState = getCurrentState();
        }
    }


    @Override
    public void stop() {

    }

    public static void main(String[] args) {
        String pkg = "com.zhihu.android";
        String act = ".App.ui.activity.MainActivity";

        AdbDevice device = new AdbDevice();
        RandomMonkey randomMonkey = new RandomMonkey(pkg, act, device);
        randomMonkey.play();
        randomMonkey.report();

    }
}
