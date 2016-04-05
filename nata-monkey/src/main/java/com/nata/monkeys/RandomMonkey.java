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
    volatile private boolean isRunning = true;
    private List<Action> actionList = new ArrayList<>();
    private final int ACTION_LIMIT = 20;
    private final int ACTION_COUNTS = 200;
    private final double P_ENTITY = 0.2;
    private final double P_SWIPE = 0.2;
    private final Random random  = new Random();
    private Action restartAction = null;
    private Action backAction = null;
    private Action menuAction = null;
    private Action homeAction = null;

    private Action lastAction = null;
    private ActionFactory actionFactory = null;
    private Set<String> activitySet = new HashSet<>();

    public RandomMonkey(String pkg, String act, AdbDevice device) {
        super("randomMonkey", pkg, act, device);
        actionFactory = new ActionFactory(device);
        restartAction = actionFactory.CreateRestartAction(getPkgAct());
        backAction = actionFactory.createBackAction();
        homeAction = actionFactory.createHomeAction();
    }

    private void startApp() {
        System.out.println("Random Monkey start playing...");
        clearAppData();
        System.out.println("cleaning app data...");

        restartAction.fire();
        actionList.add(restartAction);
        System.out.println("starting app success...");
    }

    private State getBackToApp() {
        boolean forceQuit = false;
        while (!isInCurrentPkg()) {
            // if even the restart action cannot restart it ;
            if(forceQuit){
                clearAppData();
                restartAction.fire();
                actionList.add(restartAction);
                lastAction = restartAction;
            }
            else if(lastAction instanceof StartAppAction){
                homeAction.fire();
                actionList.add(homeAction);
                restartAction.fire();
                actionList.add(restartAction);
                lastAction = restartAction;
                forceQuit = true;
            }
            // if monkey get out of pkg because of back action
            else if (lastAction instanceof BackAction) {
                restartAction.fire();
                actionList.add(restartAction);
                lastAction = restartAction;
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
        return state;
    }

    private Action chooseActionFromState(State curState) {
        Map<Action,Double> actionTable = actionFactory.getActionsFromState(curState);
        if (actionTable == null || actionTable.size() == 0) {
            System.out.println("Error cannot get actionTable or no action can be choosed");
            return backAction;
        }

        Set<Action> actionSet = actionTable.keySet();
        List<Action> list = new ArrayList<>();
        list.addAll(actionSet);
        Collections.shuffle(list);
        return list.get(0);
    }

    @Override
    public void play() {
//        System.out.println("Random Monkey start playing...");
//        restartAction.fire();
//        actionList.add(restartAction);

        startApp();

        State curState = getCurrentState();
        if (isInCurrentPkg()) {
            activitySet.add(curState.getActivity());
        }

        int cnt = 0;
        while ((++cnt) <= ACTION_COUNTS) {
            // if not in pkg, get it back
            if (!isInCurrentPkg()) {
                curState = getBackToApp();
                continue;
            }

            Action action = chooseActionFromState(curState);
            actionList.add(action);

            action.fire();
            lastAction = action;

            curState = getCurrentState();
            if (isInCurrentPkg()) {
                activitySet.add(curState.getActivity());
            }
        }
//        report();
    }




    @Override
    public void stop() {
        this.isRunning = false;
    }

    @Override
    public void report() {
        System.out.println("--------------------[Activities report]--------------------");
        for (String activity : activitySet) {
            System.out.println(activity);
        }

        System.out.println("--------------------[Widget report]--------------------");
        Set<Widget> widgetSet = getWidgetSet();
        int widgetCount = widgetSet.size();
        System.out.println("Widget count: " + widgetCount);
        for (Widget node : widgetSet) {
            System.out.println(node);
        }

        System.out.println("--------------------[Action report]--------------------");
        int actionCount = actionList.size();
        System.out.println("Actions count: " + actionCount);
        for (Action action : actionList) {
            System.out.println(action);
        }
    }

    public static void main(String[] args) {
        String pkg = "com.zhihu.android";
        String act = ".app.ui.activity.MainActivity";

//        String pkg = "random";
//        String act = "random";
        AdbDevice device = new AdbDevice();
        RandomMonkey randomMonkey = new RandomMonkey(pkg, act, device);

        randomMonkey.play();

    }
}
