package com.nata.runners;

import com.nata.AdbDevice;
import com.nata.Config;
import com.nata.monkeys.AbstractMonkey;
import com.nata.monkeys.MonkeyType;
import com.nata.monkeys.PureRandomMonkey;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-06 20:58
 */
public class PRMRunner {
    public static void main(String[] args) {
        Config config = new Config();
        config.setAlgorithm(MonkeyType.PRM);

        AbstractMonkey pureRandomMonkey= new PureRandomMonkey(config);
        pureRandomMonkey.play();
        pureRandomMonkey.report();
    }

}
