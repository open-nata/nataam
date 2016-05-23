package com.nata;

import com.nata.monkeys.MonkeyType;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-24 10:19
 */
public class Config {
    private String record_id;
    /**
     * Whether to upload to remote server
     */
    private boolean isRemote;

    private String device_id;
    private String app_name;
    private String package_name;
    private String activity_name;
    private int action_count;
    private String algorithm;
    private List<String> setup;
    private List<String> blacklist;

    public Config(){
        this.app_name = "凤城卫士";
        this.package_name = "com.cvicse.zhnt";
        this.activity_name = ".LoadingActivity";
        this.action_count = 1000;
        this.algorithm = MonkeyType.DFS;
        this.isRemote = false;
        this.setup = null;
        this.blacklist = null;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public void setRemote(boolean remote) {
        isRemote = remote;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public int getAction_count() {
        return action_count;
    }

    public void setAction_count(int action_count) {
        this.action_count = action_count;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public List<String> getSetup() {
        return setup;
    }

    public void setSetup(String setup) {
        if(setup.length() != 0){
            this.setup = new ArrayList<>();
            String [] splits = setup.trim().split("\n");
            for (String action:splits) {
                this.setup.add(action);
            }
        }
    }

    public List<String> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        if(blacklist.length() != 0){
            this.blacklist = new ArrayList<>();
            String [] splits = blacklist.trim().split("\n");
            for (String action:splits) {
                this.blacklist.add(action);
            }
        }
    }
}

