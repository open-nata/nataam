package com.nata.server;

import com.nata.Config;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-24 13:34
 */
public class RunnerFactory {
    Runner runner = null;

    public boolean isValid(){
        if(runner != null && runner.isAlive()){
            return false;
        }
        return true;
    }

    public boolean createRunnerAndRun(Config config){
        if(!isValid()){
            return false;
        }

        runner = new Runner(config);
        runner.start();
        return true;
    }
}
