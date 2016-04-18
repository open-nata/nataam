package com.nata.monkeys;

import com.nata.action.Action;
import com.nata.cmd.AdbDevice;
import com.nata.state.*;
import com.nata.utils.LogUtil;
import com.oracle.tools.packager.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-13 14:24
 */
public class DfsMonkey extends AbstractMonkey{
    private int nodeCount = 0;
    private DFSState curState;
    private DFSState rootState;
    private boolean flag = true;
    private ArrayList<Action> currentActions;

    private ArrayList<State> nodes = null;

    public DfsMonkey(int actionCount,String pkg, String act, AdbDevice device) {
        super("DfsMonkey",actionCount,pkg, act, device);
        currentActions = new ArrayList<>();
        nodes = new ArrayList<>();
    }

    private int classifyNode(DFSState state) {
        int k = DFSState.NORMAL;

        // out of this app
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

    public DfsMonkey(String pkg, String act, AdbDevice device) {
        this(1000,pkg, act, device);
    }


    private boolean goBack() {
        ActionEdge ee = curState.getFromEdge();
        if (ee != null) {
            curState = ee.getFromState();

            while (!curState.isNotOver()
                    && curState.getFromEdge() != null) {
                curState= curState.getFromEdge().getFromState();
            }

            Stack<State> nodesStack = new Stack<>();
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


            // replay
            if (tempState != null && nodesStack.contains(tempState)) {
                // this node is ancestor of current node
                while (!nodesStack.isEmpty()
                        && !nodesStack.peek().equals(tempState)) {
                    nodesStack.pop();
                    edgesStack.pop();
                }
            } else {
                getBackToApp();
            }

            while (!edgesStack.isEmpty()) {
                ActionEdge pe = edgesStack.pop();
                pe.fire();
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

    @Override
    public void startApp() {
        LogUtil.info(getName() + " start playing...");
        executeAction(getRestartAction());
        LogUtil.info("Starting app success!");
    }

    @Override
    public void play() {
        startApp();
        rootState = getCurrentDFSState();
        curState = rootState;
        addNode(rootState);

        int cnt = 0;
        while(flag && !(  curState.getFromEdge() == null && !curState.isNotOver())){
            if((++cnt) >= ACTION_COUNTS){
                LogUtil.info(" get to ACTION_COUNTS limit");
                break;
            }

            Action action = curState.getAction();
            LogUtil.debug(action.toString());

            if(action == null){
                flag = goBack();
                continue;
            }

            action.fire();

            DFSState tempNode = getCurrentDFSState();

            int kind = classifyNode(tempNode);

            switch (kind) {
                case DFSState.OLD: LogUtil.debug("old state");
                    addNode(tempNode);
                    curState= tempNode;
                    currentActions.add(action);
                    flag = goBack();
                    break;
                case DFSState.OUT: LogUtil.debug("out state");
                    addNode(tempNode);
                    curState= tempNode;
                    currentActions.add(action);
                    flag = goBack();
                    break;
                case DFSState.SAME:
                    LogUtil.debug("same state");
                    break;
                default:
                    currentActions.add(action);
                    addNode(tempNode);
                    curState = tempNode;
                    break;
            }

        }

    }
}
