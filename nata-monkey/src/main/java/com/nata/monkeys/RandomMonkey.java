package com.nata.monkeys;

import com.nata.action.Action;
import com.nata.action.BackAction;
import com.nata.action.TapAction;
import com.nata.cmd.AdbDevice;
import com.nata.element.Element;

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

    public RandomMonkey(String pkg, String act, AdbDevice device){
        super("randomMonkey",pkg,act,device);
    }

    @Override
    public void play() {
        System.out.println("start playing...");
        startApp();
        while(isRunning){
            Element nextElement = getNextElement();
            Action nextAction = null;
            if(nextElement!=  null){
                nextAction = new TapAction(nextElement,getDevice());
            }else {
                nextAction = new BackAction(getDevice());
            }
            actionList.add(nextAction);
            nextAction.fire();
            System.out.println(nextAction);
        }
        report();
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    @Override
    public void report() {
        System.out.println("report");
    }

    public static void main(String[] args) {
        String pkg = "com.zhihu.android";
        String act = ".app.ui.activity.MainActivity";

        AdbDevice device = new AdbDevice();
        RandomMonkey randomMonkey = new RandomMonkey(pkg,act,device);

        randomMonkey.startApp();
        randomMonkey.play();

    }
}
