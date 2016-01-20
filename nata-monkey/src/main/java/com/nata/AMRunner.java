package com.nata;

import com.nata.cmd.AdbDevice;
import com.nata.monkeys.RandomMonkey;
import com.nata.strategy.RandomStrategy;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-19 16:15
 */
public class AMRunner {
    public static void main(String[] args) {
        //Info about the apk to test
        String pkg = "com.zhihu.android";
        String act = "com.zhihu.android.ui.activity.GuideActivity";

        //Set Monkey settings
        AdbDevice device = new AdbDevice();
        RandomStrategy randomStrategy = new RandomStrategy();
        RandomMonkey randomMonkey = new RandomMonkey(pkg,act,device,randomStrategy);

        //Start run monkey
        randomMonkey.play();

        //Do report
        randomMonkey.report();
    }
}