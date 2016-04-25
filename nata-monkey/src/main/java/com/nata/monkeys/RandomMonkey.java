package com.nata.monkeys;

import com.nata.Config;
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

    public RandomMonkey(Config config) {
        super(config);
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
        // do report
        report();
    }


    @Override
    public void stop() {

    }
}
