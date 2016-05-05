package com.nata.action;

import com.nata.AdbDevice;
import com.nata.element.Widget;

import java.util.*;

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

    public Action CreateCleanDataAction(String pkg){
        return new CleanDataAction(device,pkg);
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


    public List<Action> getDFSActionsFromWidgets(List<Widget> widgets ) {
        List<Action>actions= new ArrayList<>();

        for (Widget widget : widgets) {
            //if not enabled, discard
            if (widget.getEnabled().equals("false")) {
                continue;
            }

            //clickable
//           (widget.getClassName().equals("android.widget.TextView")
//                    || widget.getClassName().equals("android.widget.Button")
//                    || widget.getClassName().equals("android.widget.ImageView")
//                    || widget.getClassName().equals("android.widget.RelativeLayout")
//                    || widget.getClassName().equals("android.widget.LinearLayout")
//                    || widget.getClassName().equals("android.widget.CheckedTextView")
//                    || widget.getClassName().equals("android.widget.CheckBox")
//            )

            if (widget.getClickable().equals("true")) {
                Action tapAction = CreateTapAction(widget);
                actions.add(tapAction);
            }
        }
        return actions;
    }

    public List<Action> getUIActionsFromWidgets(List<Widget> widgets ){
        List<Action>actions= new ArrayList<>();
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
                    actions.add(swipeAction);

                    swipeAction = CreateSwipeAction(widget,SwipeDirection.UP);
                    actions.add(swipeAction);
                }else if(widget.getClassName().equals("android.support.v4.view.ViewPager")){
                    Action swipeAction = CreateSwipeAction(widget,SwipeDirection.RIGHT);
                    actions.add(swipeAction);

                    swipeAction = CreateSwipeAction(widget,SwipeDirection.LEFT);
                    actions.add(swipeAction);
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
                actions.add(tapAction);
            }

            //long click actions
            if(widget.getLong_clickable().equals("true")){
                Action longClickAction = CreateLongClickAction(widget);
                actions.add(longClickAction);
            }

            //text input actions
            //TODO: can't get password from the text attribute
            if(widget.getClassName().equals("android.widget.EditText") && widget.getClickable().equals("true")){
                Action textInputAction = CreateTextInputAction(widget);
                actions.add(textInputAction);
            }

        }
        return actions;
    }

    public List<Action> getActionsFromWidgets(List<Widget> widgets ){
        List<Action>actions=  getUIActionsFromWidgets(widgets);
        Action backAction= createBackAction();
        actions.add(backAction);

        Action menuAction= createMenuAction();
        actions.add(menuAction);

        return actions;
    }
}
