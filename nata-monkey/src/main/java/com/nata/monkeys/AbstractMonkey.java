package com.nata.monkeys;

import com.nata.action.*;
import com.nata.cmd.AdbDevice;
import com.nata.dictionary.TextValueDictionary;
import com.nata.element.DumpService;
import com.nata.element.Widget;
import com.nata.rules.RuleParser;
import com.nata.rules.Rules;
import com.nata.state.State;
import com.nata.utils.LogUtil;
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
    private Set<String> activitySet = new HashSet<>();
    private List<Action> actionList = new ArrayList<>();

    private Action backAction = null;
    private Action homeAction = null;
    private Action restartAction = null;
    private Action lastAction = null;
    private ActionFactory actionFactory = null;


    public AbstractMonkey(String name, String pkg, String act, AdbDevice device) {
        this.name = name;
        this.pkg = pkg;
        this.act = act;
        this.device = device;

        actionFactory = new ActionFactory(device);
        backAction = actionFactory.createBackAction();
        homeAction = actionFactory.createHomeAction();
        restartAction = getActionFactory().CreateRestartAction(getPkgAct());
    }

    public Action getBackAction() {
        return backAction;
    }

    protected ActionFactory getActionFactory() {
        return actionFactory;
    }

    public String getPkgAct() {
        String pkgAct = this.pkg + "/" + this.act;
        return pkgAct;
    }

    public void clearAppData() {
        device.clearAppData(pkg);
        device.sleep(5000);
    }

    public void startApp() {
        LogUtil.info(name+ " start playing...");
        clearAppData();
        LogUtil.info("App data cleaned!");
        restartAction.fire();
        actionList.add(restartAction);
        LogUtil.info("Starting app success!");
    }

    /**
     * Fire the action and add the action to actionList
     * @param action to be executed
     */
    public void executeAction(Action action){
        action.fire();
        actionList.add(action);
        lastAction = action;
    }

    public State getCurrentState() {
        String curActivity = getCurrentActivity();
        String appPackage = getCurrentPackage();
        List<Widget> widgets = GrabCurrentUi();
        State state = new State(appPackage, curActivity,widgets);

        if (isInCurrentPkg()) {
            activitySet.add(state.getActivity());
        }

        return state;
    }

    public String getCurrentPackage() {
        return device.getCurrentPackageName();
    }

    public String getCurrentActivity() {
        return device.getCurrentActivity();
    }

    protected Set<String> getActivitySet() {
        return activitySet;
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
            String widgetsInfo = "CurrentUi: ";
            for (Widget widget: list) {
                widgetsInfo += widget;
                // only add the widgets in the app
                if(widget.getPackageName().equals(getPkg())){
                    widgetSet.add(widget);
                }
            }
            LogUtil.debug(widgetsInfo);
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
    public void stop(){}

    /**
     * They can report the running status after they stop
     */
    public void report(){
        LogUtil.debug("--------------------[Activities report]--------------------");
        int activityCount = activitySet.size();
        LogUtil.debug("Activity count: " + activityCount);
        for (String activity : activitySet) {
            LogUtil.debug(activity);
        }

        LogUtil.debug("--------------------[Widget report]--------------------");
        int widgetCount = widgetSet.size();
        LogUtil.debug("Widget count: " + widgetCount);
        for (Widget node : widgetSet) {
            LogUtil.debug(node.toString());
        }

        LogUtil.debug("--------------------[Action report]--------------------");
        int actionCount = actionList.size();
        LogUtil.debug("Actions count: " + actionCount);
        for (Action action : actionList) {
            LogUtil.debug(action.toString());
        }
    }

    /**
     * help the monkey to get back to app
     *
     * @return the current state
     */
    public void getBackToApp() {
        boolean forceQuit = false;
        while (!isInCurrentPkg()) {
            // if even the restart action cannot restart it ;
            if(forceQuit){
                clearAppData();
                executeAction(restartAction);
            }
            else if(lastAction instanceof StartAppAction){
                executeAction(homeAction);
                executeAction(restartAction);
                forceQuit = true;
            }
            // if monkey get out of pkg because of back action
            else if (lastAction instanceof BackAction) {
                executeAction(restartAction);
            } else {
                executeAction(backAction);
                if (!isInCurrentPkg()) {
                    executeAction(backAction);
                }
            }
        }
    }

    public void summary(int tick){
        String info = "Activity count " + getActivitySet().size() + " | ";
        info += "Widget count " + getWidgetSet().size() + " | ";
        LogUtil.info("Tick  "+ tick + " : " + info);
    }
}
