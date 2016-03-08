package com.nata.monkeys;

import com.nata.cmd.AdbDevice;
import com.nata.element.DumpService;
import com.nata.element.Element;
import com.nata.element.UINode;
import org.dom4j.DocumentException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    protected AdbDevice getDevice(){
        return device;
    }

    public Element getNextElement(){
        List<UINode> list = GrabCurrentUi();
        List<UINode> clickableList  = new ArrayList<>();
        if(list != null){
            for (UINode node: list) {
                if(node.getClickable().equals("true")){
                    clickableList.add(node);
                }
            }
            if(clickableList.size() > 0){
                int randomIndex = (int)(Math.random() * clickableList.size());
                String bounds  = clickableList.get(randomIndex).getBounds();
                return  new Element(bounds);
            }else {
                return null;
            }

        }
        return null;
    }

    private List<UINode>  GrabCurrentUi(){
        //Get dump file
        File dumpFile = device.dumpUI();
        try {
            List<UINode> list = DumpService.getNodes(dumpFile);
//            for (UINode node: list) {
//               System.out.println(node);
//            }
            return list;

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void Tap(Element element){
        device.tap(element.getX(),element.getY());
    }

    public void SleepShort(){
        device.sleep(500);
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
