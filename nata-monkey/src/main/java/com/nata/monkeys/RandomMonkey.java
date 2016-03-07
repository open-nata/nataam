package com.nata.monkeys;

import com.nata.action.Action;
import com.nata.cmd.AdbDevice;
import com.nata.element.Element;
import com.nata.strategy.Strategy;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-18 18:49
 */
public class RandomMonkey extends AbstractMonkey {
    volatile private boolean isRunning = true;

    public RandomMonkey(String pkg, String act, AdbDevice device){
        super("randomMonkey",pkg,act,device);
    }

    @Override
    public void play() {
        System.out.println("start playing...");
        startApp();
        while(isRunning){
            Element nextElement = getNextElement();
            if(nextElement!=  null){
                Tap(nextElement);
            }
            System.out.println("tap...");
        }


//        while(strategy.hasNextAction()){
//            Action action = strategy.getNextAction();
//            action.fire();
//        }
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
//        String act = "com.zhihu.android.ui.activity.GuideActivity";
        String act = ".app.ui.activity.MainActivity";

        AdbDevice device = new AdbDevice();
        RandomMonkey randomMonkey = new RandomMonkey(pkg,act,device);

        randomMonkey.startApp();
        randomMonkey.play();
//        randomMonkey.GrabCurrentUi();

    }
}
