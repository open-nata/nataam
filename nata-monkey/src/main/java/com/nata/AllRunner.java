package com.nata;

import com.nata.cmd.AdbDevice;
import com.nata.monkeys.QLearningMonkey;
import com.nata.monkeys.RandomMonkey;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-19 16:15
 */
public class AllRunner {
    public static void main(String[] args) {

        String pkg = "com.cvicse.zhnt";
        String act = ".LoadingActivity";

        //Set Monkey settings
        AdbDevice device = new AdbDevice();
        RandomMonkey randomMonkey = new RandomMonkey(pkg,act,device);
        QLearningMonkey qLearningMonkey= new QLearningMonkey(pkg,act,device);

        for(int i = 0; i  < 3; i++){
            //Start run monkey
            randomMonkey.play();

            //Do report
            randomMonkey.report();
        }

        for(int i = 0 ; i < 3 ; i++){
            //Start run monkey
            qLearningMonkey.play();

            //Do report
            qLearningMonkey.report();
        }

    }
}
