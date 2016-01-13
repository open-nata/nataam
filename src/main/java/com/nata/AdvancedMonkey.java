package com.nata;

import com.nata.cmd.AdbDevice;
import com.nata.config.Config;
import com.nata.strategy.RandomMonkeyStrategy;
import com.nata.strategy.Strategy;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 13:59
 */
public class AdvancedMonkey {

    private static AdvancedMonkey ME = null;
    private Config config = null;
    private AdbDevice device = null;
    private Strategy strategy = null;


    public static AdvancedMonkey me(){
        if(ME == null){
            ME = new AdvancedMonkey();
        }
        return ME;
    }

    private AdvancedMonkey(){
        config = new Config();
        device = new AdbDevice();
        strategy = new RandomMonkeyStrategy(device);
    }

    public void start(){
        new MonkeyRunner(config,device,strategy).run();
    }
}
