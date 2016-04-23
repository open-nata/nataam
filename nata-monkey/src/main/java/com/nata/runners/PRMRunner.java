package com.nata.runners;

import com.nata.AdbDevice;
import com.nata.monkeys.AbstractMonkey;
import com.nata.monkeys.PureRandomMonkey;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-06 20:58
 */
public class PRMRunner {
    public static void main(String[] args) {
        String pkg = "com.cvicse.zhnt";
        String act = ".LoadingActivity";

        AdbDevice device = new AdbDevice();
        AbstractMonkey pureRandomMonkey= new PureRandomMonkey(pkg, act, device);
        pureRandomMonkey.play();
        pureRandomMonkey.report();
    }

}
