package com.nata.monkeys;

import com.nata.Config;
import com.nata.action.Action;
import com.nata.AdbDevice;
import com.nata.results.TreeWeb;
import com.nata.state.*;
import com.nata.utils.HttpUtil;
import com.nata.utils.LogUtil;

import java.io.IOException;
import java.util.*;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-13 14:24
 */
public class DfsMonkey extends AbstractMonkey {
    private int nodeCount = 0;
    private DFSState curState;
    private DFSState rootState;
    private boolean flag = true;
    private ArrayList<Action> currentActions;
    private ArrayList<DFSState> nodes = null;
    private boolean isRemote = false;

    public DfsMonkey(Config config) {
        super(config);
        currentActions = new ArrayList<>();
        nodes = new ArrayList<>();
        isRemote = config.isRemote();
    }

    private int classifyNode(DFSState state) {
        int k = DFSState.NORMAL;

        // out of this App
        if (!isInCurrentPkg()) {
            state.setKind(DFSState.OUT);
            k = DFSState.OUT;
        } else {
            // nothing change
            if (curState != null && curState.equals(state)) {
                return DFSState.SAME;
            }
            if (nodes.contains(state)) {
                state.setKind(DFSState.OLD);
                k = DFSState.OLD;
            }
        }
        return k;
    }

    private void addNode(DFSState node) {
        if (nodeCount != 0) {
            new ActionEdge(curState, node, currentActions);
            currentActions.clear();
        }
        nodeCount++;
        nodes.add(node);
    }


    private boolean goBack() {
        ActionEdge ee = curState.getFromEdge();
        if (ee != null) {
            curState = ee.getFromState();

            while (!curState.isNotOver()
                    && curState.getFromEdge() != null) {
                curState = curState.getFromEdge().getFromState();
            }

            Stack<DFSState> nodesStack = new Stack<>();
            Stack<ActionEdge> edgesStack = new Stack<>();

            DFSState tempState = curState;
            while (tempState.getFromEdge() != null) {
                edgesStack.push(tempState.getFromEdge());
                tempState = tempState.getFromEdge().getFromState();
                nodesStack.push(tempState);
            }

            // attempt to one step back
            if (edgesStack.size() > 2) {
                executeAction(getBackAction());
            }

            tempState = getCurrentDFSState();

            if (curState.equals(tempState))
                return true;


            if (tempState != null && nodesStack.contains(tempState)) {
                // this node is ancestor of current node
                while (!nodesStack.isEmpty()
                        && !nodesStack.peek().equals(tempState)) {
                    nodesStack.pop();
                    edgesStack.pop();
                }
            } else {
                restartApp();
            }

            while (!edgesStack.isEmpty()) {
                ActionEdge pe = edgesStack.pop();
                executeActions(pe.getFireActions());
                // System.out.println("replay: " + pe.toString());
            }

            tempState = getCurrentDFSState();
            if (curState.equals(tempState))
                return true;
            else {
                System.err.println("go to a wrong state!");
                return goBack();
            }
        }
        return true;
    }

    private void restartApp() {
        executeAction(getRestartAction());
        String rootPa = rootState.getAppPackage();
        String rootAct = rootState.getActivity();
        int count = 0;
        while (true) {
            wait(1000);
            if (rootPa.equals(getDevice().getCurrentPackageName()) && rootAct.equals(getDevice().getCurrentActivity()))
                break;
            count++;
            if (count > 10) {
                System.err.println("Error: cannot start App!");
                return;
            }
        }
    }

    public static void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.err.println("Cannot sleep...");
            e.printStackTrace();
        }
    }

    @Override
    public void startApp() {
        LogUtil.info(getName() + " start playing...");
        executeAction(getRestartAction());
        LogUtil.info("Starting App success!");
    }

    @Override
    public void play() {
        setUp();

        startApp();
        rootState = getCurrentDFSState();
        curState = rootState;
        addNode(rootState);
        LogUtil.debug("rootState created");

        while (flag && !(curState.getFromEdge() == null && !curState.isNotOver())) {
            if (cnt >= ACTION_COUNTS) {
                LogUtil.info(" get to ACTION_COUNTS limit");
                break;
            }

            Action action = curState.getAction();


            if (action == null) {
                LogUtil.debug("action == null");
                flag = goBack();
                continue;
            }
            LogUtil.debug(action.toString());

            executeAction(action);

            DFSState tempNode = getCurrentDFSState();

            int kind = classifyNode(tempNode);

            switch (kind) {
                case DFSState.OLD:
                case DFSState.OUT:
                    LogUtil.debug("special state");
                    currentActions.add(action);
                    addNode(tempNode);
                    curState = tempNode;
                    flag = goBack();
                    break;
                case DFSState.SAME:
                    LogUtil.debug("same state");
                    break;
                default:
                    currentActions.add(action);
                    addNode(tempNode);
                    curState = tempNode;
                    LogUtil.debug("new state");
                    break;
            }
        }
        // do report
        report();


        if(isRemote) {
            updateActPath();
        }
    }

    public void updateActPath() {
        List<Action> setupActions = this.getSetupActions();
        System.out.println(setupActions);
        setupActions.add(getRestartAction());

        HashMap<String,String> actPaths = TreeWeb.getActLunchTestcases(nodes, setupActions);
        HashMap<String,String > messages = new HashMap<>();
        for (Map.Entry<String,String> entry: actPaths.entrySet()) {
            try {
                messages.clear();
                messages.put("activity_name", entry.getKey());
                messages.put("actions",entry.getValue());

                HttpUtil.postActPath(getPkg(),messages);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
