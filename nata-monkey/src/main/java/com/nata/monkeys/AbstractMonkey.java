package com.nata.monkeys;

import com.nata.action.*;
import com.nata.AdbDevice;
import com.nata.element.Widget;
import com.nata.state.DFSState;
import com.nata.state.State;
import com.nata.state.StateFactory;
import com.nata.utils.LogUtil;

import java.util.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 13:59
 */
public abstract class AbstractMonkey {
    protected final int ACTION_COUNTS;
    protected final int Frequency = 2;
    private final String name;
    private final String pkg;
    private final String act;
    private final AdbDevice device;
    private TestResult testResult;

    private Action backAction = null;
    private Action homeAction = null;
    private Action restartAction = null;
    private Action lastAction = null;
    private ActionFactory actionFactory = null;
    private StateFactory stateFactory = null;

    protected int cnt = 0;


    public AbstractMonkey(String name,int actionCount,String pkg, String act, AdbDevice device) {
        this.name = name;
        this.pkg = pkg;
        this.act = act;
        this.device = device;
        this.ACTION_COUNTS = actionCount;

        actionFactory = new ActionFactory(device);
        stateFactory = new StateFactory(device,actionFactory);

        backAction = actionFactory.createBackAction();
        homeAction = actionFactory.createHomeAction();
        restartAction = getActionFactory().CreateRestartAction(getPkgAct());

        testResult = new TestResult(name,actionCount);
    }



    public String getName(){
        return name;
    }
    public Action getBackAction() {
        return backAction;
    }


    public Action getRestartAction(){
        return restartAction;
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

    public Set<State> getStateSet(){
        return testResult.getStateSet();
    }

    public void startApp() {
        LogUtil.info(name+ " start playing...");
        clearAppData();
        LogUtil.info("App data cleaned!");
        restartAction.fire();
        testResult.addAction(restartAction);
        LogUtil.info("Starting App success!");
    }

    /**
     * Fire the action and add the action to actionList
     * @param action to be executed
     */
    public void executeAction(Action action){
        action.fire();
        testResult.addAction(action);
        lastAction = action;
        cnt++;
        if(cnt % Frequency == 0 ){
            summary();
        }
    }

    public void executeActions(List<Action> actions){
        for (Action action: actions) {
            action.fire();
            testResult.addAction(action);
            lastAction = action;
            cnt++;
            if(cnt % Frequency == 0 ){
                summary();
            }
        }
    }

    private void checkState(State state){
        // add state
        if(!testResult.containState(state)){
            testResult.addState(state);
            //add activity
            if (isInCurrentPkg()) {
                testResult.addActivity(state.getActivity());
            }
        }else {
            for (State oldState : testResult.getStateSet()) {
                if (oldState.equals(state)) {
                    state = oldState;
                    state.increaseVisit();
                }
            }
        }

        //add new widget
        List<Widget>  widgets = state.getWidgets();
//        String widgetsInfo = "CurrentUi: ";
        for (Widget widget: widgets) {
//            widgetsInfo += widget;
            // only add the widgets in the App
            if(widget.getPackageName().equals(pkg)){
                testResult.addWidget(widget);
            }
        }
//        LogUtil.debug(widgetsInfo);
    }

    public State getCurrentState() {
        State state = stateFactory.createState();
        checkState(state);
        return state;
    }


    public DFSState getCurrentDFSState(){
        DFSState dfsState = stateFactory.createDFSState();
        checkState(dfsState);
        return dfsState;
    }


    public boolean isInCurrentPkg() {
        return device.getCurrentPackageName().equals(pkg);
    }

    protected AdbDevice getDevice() {
        return device;
    }

    //TODO: detect errors,how?
    public boolean isCrashed() {
        return false;
    }

    public String getPkg() {
        return pkg;
    }

//    public void learn(String rulePath){
//        File file = new File(rulePath);
//        Rules rules = RuleParser.parse(file);
//        TextValueDictionary.getInstance().learn(rules);
//    }

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
        testResult.report();
    }

    /**
     * help the monkey to get back to App
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

    public void summary(){
        testResult.summary();
    }
}
