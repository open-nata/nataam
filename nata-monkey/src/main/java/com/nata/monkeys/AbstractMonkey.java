package com.nata.monkeys;

import com.nata.Config;
import com.nata.action.*;
import com.nata.AdbDevice;
import com.nata.element.Widget;
import com.nata.results.TestResult;
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
    private final List<String> setup;
    private TestResult testResult;

    private Action backAction = null;
    private Action homeAction = null;
    private Action restartAction = null;
    private Action cleanDataAction = null;

    private Action lastAction = null;
    private ActionFactory actionFactory = null;
    private StateFactory stateFactory = null;

    protected int cnt = 0;


    public AbstractMonkey(Config config) {
        this.name = config.getAlgorithm();
        this.pkg = config.getPackage_name();
        this.act = config.getActivity_name();
        this.ACTION_COUNTS = config.getAction_count();
        this.setup = config.getSetup();

        this.device  = new AdbDevice();
        actionFactory = new ActionFactory(device);
        stateFactory = new StateFactory(device,actionFactory);

        backAction = actionFactory.createBackAction();
        homeAction = actionFactory.createHomeAction();
        restartAction = getActionFactory().CreateRestartAction(getPkgAct());
        cleanDataAction = getActionFactory().CreateCleanDataAction(getPkg());

        testResult = new TestResult(config.getRecord_id(),config.isRemote());
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

//    public void clearAppData() {
//        device.clearAppData(pkg);
//        device.sleep(5000);
//    }

    public Set<State> getStateSet(){
        return testResult.getStateSet();
    }

    public void startApp() {
        LogUtil.info(name+ " start playing...");
        executeAction(cleanDataAction);
        executeAction(restartAction);
        LogUtil.info("Starting App success!");
    }


    public void setUp() {
        if(this.setup != null){
            for (String str: this.setup) {
                Action action = ActionParser.parse(str);
                action.fire();
            }
        }
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
                executeAction(cleanDataAction);
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


    public void toCommands(){
        testResult.toCommands();
    }
}
