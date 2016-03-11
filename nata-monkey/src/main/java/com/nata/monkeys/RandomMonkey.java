package com.nata.monkeys;

import com.nata.action.*;
import com.nata.cmd.AdbDevice;
import com.nata.element.Element;
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

    public RandomMonkey(String pkg, String act, AdbDevice device) {
        super("randomMonkey", pkg, act, device);
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
        System.out.println("start playing...");
        startApp();
        while (isRunning) {
            Action nextAction = getNextAction();
            if(nextAction == null){
                continue;
            }
            actionList.add(nextAction);
            nextAction.fire();
            System.out.println(nextAction);
            if(isCrashed()){
                System.out.println("The App Crashed");
                break;
            }
            if (actionList.size() >= ACTION_LIMIT) {
                actionList.clear();
                startApp();
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
                return new BackAction(getDevice());
            }else{
                return new MenuAction(getDevice());
            }
            //Second Gamble : Take Swipe Actions
        }else if ( (randomValue - P_ENTITY) <= P_SWIPE){
            int gamble  = random.nextInt(4);
            switch (gamble){
                case 0: return new SwipeAction(SwipeDirection.RIGHT,getDevice());
                case 1: return new SwipeAction(SwipeDirection.LEFT,getDevice());
                case 2: return new SwipeAction(SwipeDirection.UP,getDevice());
                case 3: return new SwipeAction(SwipeDirection.DOWN,getDevice());
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
                        action = new TextInputAction(new Element(nodeAction.getNode().getBounds()), getDevice());
                        break;
                    case ActionType.TAP:
                        action = new TapAction(new Element(nodeAction.getNode().getBounds()), getDevice());
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
//        String pkg = "com.zhihu.android";
//        String act = ".app.ui.activity.MainActivity";

        String pkg = "random";
        String act = "random";
        AdbDevice device = new AdbDevice();
        RandomMonkey randomMonkey = new RandomMonkey(pkg, act, device);

        randomMonkey.startApp();
        randomMonkey.play();

    }
}
