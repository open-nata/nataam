package com.nata.action;

import com.nata.cmd.AdbDevice;
import com.nata.element.Widget;
import com.nata.state.State;

import java.util.HashMap;
import java.util.List;
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

    public Action createHomeAction(){
        return new HomeAction(device);
    }

    public Action createMenuAction(){
        return  new MenuAction(device);
    }

    public Action CreateRestartAction(String pkgAct){
        return new StartAppAction(device,pkgAct);
    }

    public Action CreateSwipeAction(Widget widget,SwipeDirection direction){
        return new SwipeAction(device,widget,direction);
    }

    public Action CreateTapAction(Widget widget){
        return new TapAction(device, widget);
    }

    public Action CreateLongClickAction(Widget widget){
        return new LongClickAction(device, widget);
    }

    public Action CreateTextInputAction(Widget widget){
        return new TextInputAction(device, widget);
    }


    public Map<Action,Double> getActionsFromState(State state){
        Map<Action,Double> actionTable = new HashMap<>();
        Action backAction= createBackAction();
        actionTable.put(backAction,backAction.getReward());

        Action menuAction= createMenuAction();
        actionTable.put(menuAction,menuAction.getReward());

        List<Widget> widgets = state.getWidgetSet();

        for(Widget widget: widgets){
            //if not enabled, discard
            if(widget.getEnabled().equals("false")){
                continue;
            }

            // if scrollable
            //TODO to make the swipte actions to adapt to element bounds;
            if(widget.getScrollable().equals("true")){
                if(widget.getClassName().equals("android.widget.ListView")){
                    Action swipeAction = CreateSwipeAction(widget,SwipeDirection.DOWN);
                    actionTable.put(swipeAction,swipeAction.getReward());

                    swipeAction = CreateSwipeAction(widget,SwipeDirection.UP);
                    actionTable.put(swipeAction,swipeAction.getReward());
                }else if(widget.getClassName().equals("android.support.v4.view.ViewPager")){
                    Action swipeAction = CreateSwipeAction(widget,SwipeDirection.RIGHT);
                    actionTable.put(swipeAction,swipeAction.getReward());

                    swipeAction = CreateSwipeAction(widget,SwipeDirection.LEFT);
                    actionTable.put(swipeAction,swipeAction.getReward());
                }
            }



            //clickable
            if( (      widget.getClassName().equals("android.widget.TextView")
                    || widget.getClassName().equals("android.widget.Button")
                    || widget.getClassName().equals("android.widget.ImageView")
                    || widget.getClassName().equals("android.widget.RelativeLayout")
                    || widget.getClassName().equals("android.widget.LinearLayout")
                    || widget.getClassName().equals("android.widget.CheckedTextView")
                    || widget.getClassName().equals("android.widget.CheckBox")
            )
                    && widget.getClickable().equals("true") ){
                Action tapAction  = CreateTapAction(widget);
                actionTable.put(tapAction,tapAction.getReward());
            }

            //long click actions
            if(widget.getLong_clickable().equals("true")){
                Action longClickAction = CreateLongClickAction(widget);
                actionTable.put(longClickAction,longClickAction.getReward());
            }

            //text input actions
            //TODO: can't get password from the text attribute
            if(widget.getClassName().equals("android.widget.EditText") && widget.getClickable().equals("true")){
                    Action textInputAction = CreateTextInputAction(widget);
                    actionTable.put(textInputAction,textInputAction.getReward());
            }

        }
        return actionTable;
    }
}
