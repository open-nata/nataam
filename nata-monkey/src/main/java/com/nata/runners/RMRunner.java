package com.nata.runners;

import com.nata.AdbDevice;
import com.nata.monkeys.RandomMonkey;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-19 16:15
 */
public class RMRunner {
    public static void main(String[] args) {
//      String pkg = "com.zhihu.android";
//      String act = ".App.ui.activity.MainActivity";

        String pkg = "com.cvicse.zhnt";
        String act = ".LoadingActivity";
        AdbDevice device = new AdbDevice();
        RandomMonkey randomMonkey = new RandomMonkey(pkg, act, device);
        randomMonkey.play();
        randomMonkey.report();
    }
}
