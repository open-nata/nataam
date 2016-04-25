package com.nata.monkeys;

import com.nata.action.Action;
import com.nata.element.Widget;
import com.nata.state.State;
import com.nata.utils.HttpUtil;
import com.nata.utils.LogUtil;

import java.io.IOException;
import java.util.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-15 11:15
 */
public class TestResult {
    private String recordId;
    private boolean isRemote = true;

    private Set<Widget> widgetSet;
    private Set<String> activitySet;
    private Set<State> stateSet;
    private List<Action> actionList;
    private List<String> summaryList;



    public TestResult(String recordId,boolean isRemote){

        this.recordId = recordId;
        this.isRemote = isRemote;

        widgetSet = new HashSet<>();
        activitySet = new HashSet<>();
        stateSet = new HashSet<>();
        actionList = new ArrayList<>();
        summaryList = new ArrayList<>();
    }

    public void addWidget(Widget widget){
        if(widgetSet.add(widget) && isRemote){
            try {
                HttpUtil.postWidget(recordId, widget.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addActivity(String activity){
        if(activitySet.add(activity) && isRemote){
            try {
                HttpUtil.postActivity(recordId,activity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Set<State> getStateSet(){
        return stateSet;
    }

    public void addState(State state){
        if(stateSet.add(state) && isRemote){
            try {
                HttpUtil.postState(recordId,state.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean containState(State state){
        return stateSet.contains(state);
    }

    public void addAction(Action action){
        actionList.add(action);
        if(isRemote){
            try {
                HttpUtil.postAction(recordId,action.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int actionSize(){
        return actionList.size();
    }

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

        LogUtil.debug("--------------------[State report]--------------------");
        int stateCount = stateSet.size();
        LogUtil.debug("State count: " + stateCount);
        for (State state : stateSet) {
            LogUtil.debug(state.toString());
        }
    }

    public void summary(){
        String info = "Action count " + actionList.size() + " | ";
        info += "Activity count " + activitySet.size() + " | ";
        info += "Widget count " + widgetSet.size()  + " | ";
        info += "States count " + stateSet.size() + " | ";
        summaryList.add(info);

        LogUtil.info("Tick  "+ summaryList.size()+ " : " + info);

        HashMap<String,String> summary = new HashMap<>();
        summary.put("action",actionList.size()+"");
        summary.put("activity",activitySet.size()+"");
        summary.put("widget",widgetSet.size()+"");
        summary.put("state",stateSet.size()+"");

        if(isRemote){
            try {
                HttpUtil.postSummary(recordId,summary);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
