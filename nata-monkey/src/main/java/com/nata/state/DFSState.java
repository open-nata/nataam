package com.nata.state;

import com.nata.action.Action;
import com.nata.element.Widget;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-18 00:06
 */
public class DFSState extends State{
    private Iterator<Action> it;
    public DFSState(String appPackage, String activity, List<Widget> widgetList, Map<Action, Double> actionMap) {
        super(appPackage, activity, widgetList, actionMap);
        Set<Action> actions= actionMap.keySet();
        it = actions.iterator();
    }

    public boolean isNotOver(){
        return it.hasNext();
    }

    public Action getAction() {
        if (it.hasNext())
            return it.next();
        return null;
    }
}
