package com.nata.monkeys;

import com.nata.action.Action;
import com.nata.action.TextInputAction;
import com.nata.cmd.AdbDevice;
import com.nata.element.DumpService;
import com.nata.element.Element;
import com.nata.element.UINode;
import com.nata.rules.Rule;
import com.nata.rules.RuleParser;
import com.nata.rules.Rules;
import com.nata.state.State;
import org.dom4j.DocumentException;
import org.dom4j.Text;
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
    private Set<UINode> widgetSet = new HashSet<>();
    private Map<String,String> knowledge = new HashMap<>();

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
    }


    public State getCurrentState() {
        String curActivity = getCurrentActivity();
        String appPackage = getCurrentPackage();
        State state = new State(appPackage, curActivity);
        List<UINode> uiNodes = GrabCurrentUi();


        // Add the uinodes with resourceId;
        for (UINode node : uiNodes) {
            if (!node.getResourceId().equals("")) {
                state.addUINode(node);
            }
        }

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


    protected List<UINode> GrabCurrentUi() {
        //Get dump file
        File dumpFile = device.dumpUI();
        try {
            List<UINode> list = DumpService.getNodes(dumpFile);
            for (UINode node : list) {
//                System.out.println(node);
                widgetSet.add(node);
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

    protected Set<UINode> getWidgetSet(){
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


    public void brainStorm(Action action){
        if(action instanceof TextInputAction){
            TextInputAction textInputAction = (TextInputAction) action;
            String resourceId = textInputAction.getElement().getResourceId();
            String value = knowledge.get(resourceId);
            if(value != null){
                textInputAction.setText(value);
            }
        }
    }
    public void learn(String rulePath){
        File file = new File(rulePath);
        Rules rules = RuleParser.parse(file);
        learn(rules);
    }

    public void learn(Rules rules){
        List<Rule> ruleList = rules.getRules();
        for (Rule rule:ruleList) {
            knowledge.put(rule.getResouceId(),rule.getValue());
        }
    }

    public void learn(Rule rule){
        knowledge.put(rule.getResouceId(),rule.getValue());
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
