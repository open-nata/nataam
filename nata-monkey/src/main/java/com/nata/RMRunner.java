package com.nata;

import com.nata.cmd.AdbDevice;
import com.nata.monkeys.RandomMonkey;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-19 16:15
 */
public class RMRunner {
    public static void main(String[] args) {
        //Info about the apk to test
//        String pkg = "com.zhihu.android";
//        String act = ".app.ui.activity.MainActivity";

        String pkg = "com.cvicse.zhnt";
        String act = ".LoadingActivity";
        //Set Monkey settings
        AdbDevice device = new AdbDevice();
        RandomMonkey randomMonkey = new RandomMonkey(pkg,act,device);

        for(int i = 0; i  < 3; i++){
            //Start run monkey
            randomMonkey.play();

            //Do report
            randomMonkey.report();
        }

    }
}