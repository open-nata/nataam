package com.nata.runners;

import com.nata.Config;
import com.nata.monkeys.AbstractMonkey;
import com.nata.monkeys.DfsMonkey;
import com.nata.monkeys.MonkeyType;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-17 23:32
 */
public class DFSRunner{

    public static void main(String[] args) {
        Config config = new Config();
        config.setAlgorithm(MonkeyType.DFS);

        AbstractMonkey dfsMonkey= new DfsMonkey(config);
        dfsMonkey.play();
        dfsMonkey.report();

    }
}
