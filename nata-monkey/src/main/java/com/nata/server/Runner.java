package com.nata.server;

import com.nata.AdbDevice;
import com.nata.Config;
import com.nata.monkeys.*;
import com.nata.utils.LogUtil;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-19 16:15
 */
public class Runner  extends Thread{
    AbstractMonkey monkey;

    public Runner(Config config){
        String algorithm = config.getAlgorithm();

        switch (algorithm){
            case "dfs" : monkey = new DfsMonkey(config);break;
            case "prm" : monkey = new PureRandomMonkey(config);break;
            case "rm"  : monkey = new RandomMonkey(config);break;
            case "qlm" : monkey = new QLearningMonkey(config);break;
            default    : monkey = new DfsMonkey(config);break;
        }
    }

    @Override
    public void run() {
        monkey.play();
    }


    public static void main(String[] args) {
        Config config = new Config();
        config.setAlgorithm("dfs");
        config.setAction_count(1000);
        config.setApp_name("凤城卫士");
        config.setPackage_name("com.cvicse.zhnt");
        config.setActivity_name(".LoadingActivity");
        Runner runner = new Runner(config);
        runner.start();
    }
}
