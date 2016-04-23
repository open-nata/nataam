package com.nata.runners;

import com.nata.AdbDevice;
import com.nata.monkeys.AbstractMonkey;
import com.nata.monkeys.DfsMonkey;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-17 23:32
 */
public class DFSRunner {

    public static void main(String[] args) {
        String pkg = "com.cvicse.zhnt";
        String act = ".LoadingActivity";

        AdbDevice device = new AdbDevice();
        AbstractMonkey dfsMonkey= new DfsMonkey(pkg, act, device);
        dfsMonkey.play();
        dfsMonkey.report();

    }
}
