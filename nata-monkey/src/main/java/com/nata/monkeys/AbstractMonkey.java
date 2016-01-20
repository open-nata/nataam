package com.nata.monkeys;

import com.nata.cmd.AdbDevice;

import java.io.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 13:59
 */
public abstract class AbstractMonkey {
    private final String name ;
    private final String pkg ;
    private final String act ;
    private final AdbDevice device;

    /**
     *
     * @param name
     * @param pkg
     * @param act
     * @param device
     */
    public AbstractMonkey(String name,String pkg,String act,AdbDevice device){
        this.name = name;
        this.pkg = pkg;
        this.act = act;
        this.device = device;
    }

    public void startApp(){
        String pkgAct = this.getPkg()+"/"+ this.getAct();
        device.startActivity(pkgAct);
    }

    public void GrabCurrentUi(){
        File dumpFile = device.dumpUI();
        // try with resources
        try (BufferedReader reader = new BufferedReader(new FileReader(dumpFile))){
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getAct() {
        return act;
    }

    public String getPkg() {
        return pkg;
    }

    /**
     * The monkeys know how to play with the device
     */
    public abstract void play();

    /**
     * They can stop whenever we ask them to
     */
    public abstract void stop();

    /**
     * They can report the running status after they stop
     */
    public abstract void report();
}
