package com.nata.monkeys;

import com.nata.action.*;
import com.nata.cmd.AdbDevice;
import com.nata.element.Widget;
import com.nata.element.UINode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-18 18:49
 */
public class RandomMonkey extends AbstractMonkey {
    volatile private boolean isRunning = true;
    private List<Action> actionList = new ArrayList<>();
    private final int ACTION_LIMIT = 20;
    private final double P_ENTITY = 0.2;
    private final double P_SWIPE = 0.2;
    private final Random random  = new Random();
    private Action restartAction = null;
    private Action backAction = null;
    private Action menuAction = null;
    private Action lastAction = null;
    private ActionFactory actionFactory = null;

    public RandomMonkey(String pkg, String act, AdbDevice device) {
        super("randomMonkey", pkg, act, device);
        actionFactory = new ActionFactory(device);
        restartAction = actionFactory.CreateRestartAction(getPkgAct());
        backAction = actionFactory.createBackAction();
    }


    private class UINodeAction {
        UINode node;
        String Action;

        UINodeAction(UINode node, String Action) {
            this.node = node;
            this.Action = Action;
        }

        public UINode getNode() {
            return node;
        }

        public String getAction() {
            return Action;
        }
    }

    @Override
    public void play() {
        System.out.println("Random Monkey start playing...");
        restartAction.fire();
        actionList.add(restartAction);

        while (isRunning) {
            Action nextAction;
            // if not in current pkg
            if(!isInCurrentPkg()){
                // if monkey get out of pkg because of back action
                if(lastAction instanceof BackAction){
                    nextAction = restartAction;
                }else {
                    nextAction = backAction;
                }
            }else{
                nextAction= getNextAction();
            }

            if(nextAction == null){
                continue;
            }


            actionList.add(nextAction);
            nextAction.fire();
            lastAction = nextAction;

            System.out.println(nextAction);
            if(isCrashed()){
                System.out.println("The App Crashed");
                break;
            }

            if (actionList.size() >= ACTION_LIMIT) {
                actionList.clear();
                restartAction.fire();
                actionList.add(restartAction);
            }
        }
        report();
    }


    public Action getNextAction() {
        //First Gamble : Whether to Take Functional Actions: Back & Menu
        double randomValue = Math.random();
        if(randomValue < P_ENTITY){
            double gamble = Math.random();
            if(gamble > 0.5){
                return backAction;
            }else{
                return menuAction;
            }
            //Second Gamble : Take Swipe Actions
        }else if ( (randomValue - P_ENTITY) <= P_SWIPE){
            int gamble  = random.nextInt(4);
            switch (gamble){
                case 0: actionFactory.CreateSwipeAction(SwipeDirection.LEFT);break;
                case 1: actionFactory.CreateSwipeAction(SwipeDirection.RIGHT);break;
                case 2: actionFactory.CreateSwipeAction(SwipeDirection.UP);break;
                case 3: actionFactory.CreateSwipeAction(SwipeDirection.DOWN);break;
            }
        }

        //Action nextAction;

        List<UINode> list = GrabCurrentUi();
        List<UINodeAction> actionList = new ArrayList<>();
        if (list != null) {
            for (UINode node : list) {
                if (node.getClassName().equals("android.widget.Button") && node.getClickable().equals("true")) {
                    actionList.add(new UINodeAction(node, ActionType.TAP));
                }
                if (node.getClassName().equals("android.widget.EditText") && node.getText().length() == 0) {
                    actionList.add(new UINodeAction(node, ActionType.INPUT));
                }
            }
            if (actionList.size() > 0) {
                int randomIndex = (int) (Math.random() * actionList.size());
                UINodeAction nodeAction = actionList.get(randomIndex);
                Action action = null;
                switch (nodeAction.getAction()) {
                    case ActionType.INPUT:
                        action = new TextInputAction(getDevice(),new Widget(nodeAction.getNode()));
                        break;
                    case ActionType.TAP:
                        action = new TapAction( getDevice(),new Widget(nodeAction.getNode()));
                        break;
                }
                return action;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    @Override
    public void report() {
        for (Action action:actionList) {
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
