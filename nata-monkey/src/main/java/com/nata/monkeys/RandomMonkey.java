package com.nata.monkeys;

import com.nata.action.*;
import com.nata.cmd.AdbDevice;
import com.nata.element.Element;
import com.nata.element.UINode;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-18 18:49
 */
public class RandomMonkey extends AbstractMonkey {
    volatile private boolean isRunning = true;
    private List<Action> actionList = new ArrayList<>();
    private final int ACTION_LIMIT = 20;

    public RandomMonkey(String pkg, String act, AdbDevice device){
        super("randomMonkey",pkg,act,device);
    }


    private class UINodeAction{
        UINode node;
        String Action;
        UINodeAction(UINode node,String Action){
            this.node = node;
            this.Action = Action;
        }
        public UINode getNode(){
            return node;
        }
        public String getAction(){
            return Action;
        }
    }

    @Override
    public void play() {
        System.out.println("start playing...");
        startApp();
        while(isRunning){
            Action nextAction = getNextUIAction();
            if(nextAction==  null){
                nextAction = new BackAction(getDevice());
            }
//            Action nextAction ;
//            if(Math.random() > 0.5){
//                nextAction = new SwipeAction(SwipeDirection.RIGHT,getDevice());
//            }else{
//                nextAction = new SwipeAction(SwipeDirection.LEFT,getDevice());
//            }
            actionList.add(nextAction);
            nextAction.fire();
            System.out.println(nextAction);
            if(actionList.size() >= ACTION_LIMIT){
               break;
            }
        }
        report();
    }


    public Action getNextUIAction(){
        List<UINode> list = GrabCurrentUi();
        List<UINodeAction> actionList  = new ArrayList<>();
        if(list != null){
            for (UINode node: list) {
                if(node.getClassName().equals("android.widget.Button") && node.getClickable().equals("true")){
                    actionList.add(new UINodeAction(node, ActionType.TAP));
                }
                if(node.getClassName().equals("android.widget.EditText") && node.getText().length()==0){
                    actionList.add(new UINodeAction(node, ActionType.INPUT));
                }
            }
            if(actionList.size() > 0){
                int randomIndex = (int)(Math.random() * actionList.size());
                UINodeAction nodeAction = actionList.get(randomIndex);
                Action action = null ;
                switch (nodeAction.getAction()){
                    case  ActionType.INPUT :action = new TextInputAction(new Element(nodeAction.getNode().getBounds()),getDevice());break;
                    case  ActionType.TAP :action = new TapAction(new Element(nodeAction.getNode().getBounds()),getDevice());break;
                }
                return  action;
            }else {
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
        System.out.println(actionList);
    }

    public static void main(String[] args) {
//        String pkg = "com.zhihu.android";
//        String act = ".app.ui.activity.MainActivity";

        String pkg = "random";
        String act = "random";
        AdbDevice device = new AdbDevice();
        RandomMonkey randomMonkey = new RandomMonkey(pkg,act,device);

        randomMonkey.startApp();
        randomMonkey.play();

    }
}
