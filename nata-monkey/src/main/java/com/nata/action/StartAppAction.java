package com.nata.action;

import com.nata.AdbDevice;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-13 17:04
 */
public class StartAppAction extends Action{
    private AdbDevice device = null;
    private String pkgAct = null;

    public StartAppAction(AdbDevice device, String pkgAct){
        super(ActionType.START_APP);
        this.device = device;
        this.pkgAct = pkgAct;
    }

    @Override
    public void fire() {
        device.startActivity(pkgAct);
        device.sleep(2000);
        count++;
    }

    @Override
    public double getReward() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString()+"StartAppAction{" +
                "pkgAct='" + pkgAct + '\'' +
                '}';
    }
//
//    @Override
//    public int hashCode() {
//        int hash = 17;
//        hash = 31 * hash + pkgAct.hashCode();
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object otherObject) {
//        if (this == otherObject) {
//            return true;
//        }
//
//        if (otherObject == null) {
//            return false;
//        }
//
//        if (getClass() != otherObject.getClass()) {
//            return false;
//        }
//
//        StartAppAction other = (StartAppAction) otherObject;
//
//        if(!pkgAct.equals(other.pkgAct)){
//            return false;
//        }
//
//        return true;
//    }
}
