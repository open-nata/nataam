package com.nata;

import com.nata.action.Action;
import com.nata.cmd.AdbDevice;
import com.nata.config.Config;
import com.nata.strategy.Strategy;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 16:40
 */
public class MonkeyRunner {
    private Config config = null;
    private AdbDevice device = null;
    private Strategy strategy = null;

    public MonkeyRunner(Config config, AdbDevice device, Strategy strategy) {
        this.config = config;
        this.device = device;
        this.strategy = strategy;
    }

    public void run(){
        while(strategy.hasNextAction()){
            Action action = strategy.getNextAction();
            action.fire();
        }
    }
}
