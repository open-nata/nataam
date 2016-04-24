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
        AdbDevice device = new AdbDevice();
        String appName = config.getApp_name();
        String deviceId = config.getDevice_id();
        int actionCount = config.getAction_count();
        String packageName = config.getPackage_name();
        String activityName = config.getActivity_name();
        String algorithm = config.getAlgorithm();
        LogUtil.info("[Algorithm]: " + algorithm + " [App name]: " + appName + " [Device]: " + deviceId);

        switch (algorithm){
            case "dfs" : monkey = new DfsMonkey(actionCount,packageName,activityName,device);break;
            case "prm" : monkey = new PureRandomMonkey(actionCount,packageName,activityName,device);break;
            case "rm"  : monkey = new RandomMonkey(actionCount,packageName,activityName,device);break;
            case "qlm" : monkey = new QLearningMonkey(actionCount,packageName,activityName,device);break;
            default    : monkey = new DfsMonkey(actionCount,packageName,activityName,device);break;
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
