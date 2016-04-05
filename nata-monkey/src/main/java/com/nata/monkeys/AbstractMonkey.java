package com.nata.monkeys;

import com.nata.action.Action;
import com.nata.action.TextInputAction;
import com.nata.cmd.AdbDevice;
import com.nata.dictionary.TextValueDictionary;
import com.nata.element.DumpService;
import com.nata.element.Widget;
import com.nata.rules.Rule;
import com.nata.rules.RuleParser;
import com.nata.rules.Rules;
import com.nata.state.State;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 13:59
 */
public abstract class AbstractMonkey {
    private final String name;
    private final String pkg;
    private final String act;
    private final AdbDevice device;
    private Set<Widget> widgetSet = new HashSet<>();

    public AbstractMonkey(String name, String pkg, String act, AdbDevice device) {
        this.name = name;
        this.pkg = pkg;
        this.act = act;
        this.device = device;
    }

    public String getPkgAct() {
        String pkgAct = this.pkg + "/" + this.act;
        return pkgAct;
    }

    public void clearAppData() {
        device.clearAppData(pkg);
        device.sleep(5000);
    }


    public State getCurrentState() {
        String curActivity = getCurrentActivity();
        String appPackage = getCurrentPackage();
        List<Widget> widgets = GrabCurrentUi();
        State state = new State(appPackage, curActivity,widgets);


//        for (Widget widget : widgets) {
////            if (!widget.getResourceId().equals("")) {
//                state.addWidget(widget);
////            }
//        }


        return state;
    }

    public String getCurrentPackage() {
        return device.getCurrentPackageName();
    }

    public String getCurrentActivity() {
        return device.getCurrentActivity();
    }


    public boolean isInCurrentPkg() {
        return device.getCurrentPackageName().equals(pkg);
    }

    protected AdbDevice getDevice() {
        return device;
    }


    protected List<Widget> GrabCurrentUi() {
        File dumpFile = device.dumpUI();
        try {
            List<Widget> list = DumpService.getNodes(dumpFile);
            for (Widget widget: list) {
                // only add the widgets in the app
//                System.out.println(widget);
                if(widget.getPackageName().equals(getPkg())){
                    widgetSet.add(widget);
                }
            }
            return list;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected Set<Widget> getWidgetSet(){
        return widgetSet;
    }

    //TODO: detect errors,how?
    public boolean isCrashed() {
        return false;
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

    public void learn(String rulePath){
        File file = new File(rulePath);
        Rules rules = RuleParser.parse(file);
        TextValueDictionary.getInstance().learn(rules);
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
