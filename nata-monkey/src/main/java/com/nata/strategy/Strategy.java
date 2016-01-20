package com.nata.strategy;

import com.nata.action.Action;
import com.nata.cmd.AdbDevice;

import java.util.ArrayList;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 16:20
 */
public interface Strategy {

    Action getNextAction();

    boolean hasNextAction();
}
