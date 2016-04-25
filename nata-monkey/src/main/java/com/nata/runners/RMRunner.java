package com.nata.runners;

import com.nata.AdbDevice;
import com.nata.Config;
import com.nata.monkeys.MonkeyType;
import com.nata.monkeys.RandomMonkey;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-19 16:15
 */
public class RMRunner {
    public static void main(String[] args) {
        Config config = new Config();
        config.setAlgorithm(MonkeyType.RM);

        RandomMonkey randomMonkey = new RandomMonkey(config);
        randomMonkey.play();
    }
}
