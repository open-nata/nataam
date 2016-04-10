package com.nata.monkeys;

import com.nata.action.*;
import com.nata.cmd.AdbDevice;
import com.nata.element.Widget;
import com.nata.state.State;

import java.util.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-18 18:49
 */
public class RandomMonkey extends AbstractMonkey {
    private final int ACTION_COUNTS = 1000;
    private final int Frequency = 100;
    private Set<String> activitySet = new HashSet<>();

    public RandomMonkey(String pkg, String act, AdbDevice device) {
        super("randomMonkey", pkg, act, device);
    }

    private Action chooseActionFromState(State curState) {
        Map<Action, Double> actionTable = getActionFactory().getActionsFromState(curState);
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

        int cnt = 0;
        while ((++cnt) <= ACTION_COUNTS) {
            // if not in pkg, get it back
            if (!isInCurrentPkg()) {
                getBackToApp();
                curState = getCurrentState();
                continue;
            }

            Action action = chooseActionFromState(curState);

            action.fire();

            curState = getCurrentState();

            if(cnt % Frequency== 0){
                summary(cnt/Frequency);
            }
        }
    }


    @Override
    public void stop() {

    }

    public static void main(String[] args) {
        String pkg = "com.zhihu.android";
        String act = ".app.ui.activity.MainActivity";

        AdbDevice device = new AdbDevice();
        RandomMonkey randomMonkey = new RandomMonkey(pkg, act, device);
        randomMonkey.play();
        randomMonkey.report();

    }
}
