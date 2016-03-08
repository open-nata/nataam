package com.nata;

import com.nata.cmd.AdbDevice;
import com.nata.monkeys.RandomMonkey;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-19 16:15
 */
public class AMRunner {
    public static void main(String[] args) {
        //Info about the apk to test
        String pkg = "com.zhihu.android";
        String act = ".app.ui.activity.MainActivity";

        //Set Monkey settings
        AdbDevice device = new AdbDevice();
        RandomMonkey randomMonkey = new RandomMonkey(pkg,act,device);

        //Start run monkey
        randomMonkey.play();

        //Do report
        randomMonkey.report();
    }
}
