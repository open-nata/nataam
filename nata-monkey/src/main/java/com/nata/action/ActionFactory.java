package com.nata.action;

import com.nata.cmd.AdbDevice;
import com.nata.element.Element;
import com.nata.element.UINode;
import com.nata.state.State;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-03-24 15:21
 */
public class ActionFactory {
    private AdbDevice device = null;

    public ActionFactory(AdbDevice device) {
        this.device = device;
    }

    public Action createBackAction(){
        return new BackAction(device);
    }

    public Action createMenuAction(){
        return  new MenuAction(device);
    }

    public Action CreateRestartAction(String pkgAct){
        return new StartAppAction(device,pkgAct);
    }

    public Action CreateSwipeAction(SwipeDirection direction){
        return new SwipeAction(device,direction);
    }

    public Action CreateTapAction(Element element){
        return new TapAction(device,element);
    }

    public Action CreateLongClickAction(Element element){
        return new LongClickAction(device,element);
    }

    public Action CreateTextInputAction(Element element){
        return new TextInputAction(device,element);
    }


    public Map<Action,Double> getActionsFromState(State state){
        Map<Action,Double> actionTable = new HashMap<>();
        Action backAction= createBackAction();
        actionTable.put(backAction,backAction.getReward());

//        actionTable.put(createMenuAction(),Action.RINIT);
        Set<UINode> uiList = state.getUIList();
        for(UINode node : uiList){
            //if not enabled, discard
            if(node.getEnabled().equals("false")){
                continue;
            }
            // if scrollable
            //TODO to make the swipte actions to adapt to element bounds;
            if(node.getScrollable().equals("true")){
                Action swipeAction = CreateSwipeAction(SwipeDirection.RIGHT);
                actionTable.put(swipeAction,swipeAction.getReward());

                swipeAction = CreateSwipeAction(SwipeDirection.LEFT);
                actionTable.put(swipeAction,swipeAction.getReward());

                swipeAction = CreateSwipeAction(SwipeDirection.DOWN);
                actionTable.put(swipeAction,swipeAction.getReward());

                swipeAction = CreateSwipeAction(SwipeDirection.UP);
                actionTable.put(swipeAction,swipeAction.getReward());
            }

            //click button actions
            if( node.getClassName().equals("android.widget.Button") && node.getClickable().equals("true") ){
                Element element = new Element(node);
                Action tapAction  = CreateTapAction(element);
                actionTable.put(tapAction,tapAction.getReward());
            }

            //long click actions
            if(node.getLong_clickable().equals("true")){
                Element element = new Element(node);
                Action longClickAction = CreateLongClickAction(element);
                actionTable.put(longClickAction,longClickAction.getReward());
            }

            //text input actions
            //TODO: can't get password from the text attribute
            if(node.getClassName().equals("android.widget.EditText") && node.getClickable().equals("true")){
                if(node.getText().equals("")){
                    Element element = new Element(node);
                    Action textInputAction = CreateTextInputAction(element);
                    actionTable.put(textInputAction,textInputAction.getReward());
                }
            }
        }
        return actionTable;
    }
}
