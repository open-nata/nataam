package com.nata.monkeys;

import com.nata.action.Action;
import com.nata.cmd.AdbDevice;
import com.nata.strategy.RandomStrategy;
import com.nata.strategy.Strategy;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-18 18:49
 */
public class RandomMonkey extends AbstractMonkey {
    // try to use strategy design mode
    private Strategy strategy = null;

    public RandomMonkey(String pkg, String act, AdbDevice device, Strategy strategy){
        super("randomMonkey",pkg,act,device);
        this.strategy = strategy;
    }



    @Override
    public void play() {
        System.out.println("start playing...");
        //Start target apk
        startApp();

//        while(strategy.hasNextAction()){
//            Action action = strategy.getNextAction();
//            action.fire();
//        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void report() {
        System.out.println("report");
    }

    public static void main(String[] args) {
        String pkg = "com.zhihu.android";
        String act = "com.zhihu.android.ui.activity.GuideActivity";

        AdbDevice device = new AdbDevice();
        RandomStrategy randomStrategy = new RandomStrategy();
        RandomMonkey randomMonkey = new RandomMonkey(pkg,act,device,randomStrategy);

        randomMonkey.startApp();
        randomMonkey.GrabCurrentUi();
    }
}
