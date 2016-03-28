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
        //add the actions which all states share;
        actionTable.put(createBackAction(),Action.RINIT);
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
                actionTable.put(CreateSwipeAction(SwipeDirection.RIGHT),Action.RINIT);
                actionTable.put(CreateSwipeAction(SwipeDirection.LEFT),Action.RINIT);
                actionTable.put(CreateSwipeAction(SwipeDirection.DOWN),Action.RINIT);
                actionTable.put(CreateSwipeAction(SwipeDirection.UP),Action.RINIT);
            }

            //click button actions
            if( node.getClassName().equals("android.widget.Button") && node.getClickable().equals("true") ){
                Element element = new Element(node);
                actionTable.put(CreateTapAction(element),Action.RINIT);
            }

            //long click actions
            if(node.getLong_clickable().equals("true")){
                Element element = new Element(node);
                actionTable.put(CreateLongClickAction(element),Action.RINIT);
            }

            //text input actions, can't get password from the text attribute
            if(node.getClassName().equals("android.widget.EditText") && node.getClickable().equals("true")){
                if(node.getText().equals("")){
                    Element element = new Element(node);
                    actionTable.put(CreateTextInputAction(element),Action.RINIT);
                }
            }
        }
        return actionTable;
    }
}
