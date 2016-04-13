package com.nata.monkeys;

import com.nata.cmd.AdbDevice;
import com.nata.state.State;
import com.nata.state.StateGraph;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-13 14:24
 */
public class DfsMonkey extends AbstractMonkey{

    private State rootState;
    private State curState;
    private StateGraph tree = new StateGraph();

    public DfsMonkey(String pkg, String act, AdbDevice device) {
        super("DfsMonkey",pkg, act, device);
    }

    @Override
    public void play() {
        startApp();
        rootState = getCurrentState();

        curState = rootState;

    }
}
