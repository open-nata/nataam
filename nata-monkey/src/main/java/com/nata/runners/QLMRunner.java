package com.nata.runners;

import com.nata.AdbDevice;
import com.nata.Config;
import com.nata.monkeys.AbstractMonkey;
import com.nata.monkeys.MonkeyType;
import com.nata.monkeys.QLearningMonkey;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-19 16:15
 */
public class QLMRunner {
    public static void main(String[] args) {
        Config config = new Config();
        config.setAlgorithm(MonkeyType.QLM);

        AbstractMonkey qLearningMonkey = new QLearningMonkey(config);
        //      qLearningMonkey.learn("rules/zhihu.json");
        qLearningMonkey.play();
    }
}
