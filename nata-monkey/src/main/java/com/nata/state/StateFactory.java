package com.nata.state;

import com.nata.action.Action;
import com.nata.action.ActionFactory;
import com.nata.cmd.AdbDevice;
import com.nata.element.DumpService;
import com.nata.element.Widget;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-18 00:22
 */
public class StateFactory {
    public AdbDevice device = null;
    private ActionFactory actionFactory = null;

    public StateFactory(AdbDevice device,ActionFactory actionFactory){
        this.device = device;
        this.actionFactory = actionFactory;
    }

    public State createState(){
        String curActivity = device.getCurrentActivity();
        String appPackage = device.getCurrentPackageName();
        List<Widget> widgets = GrabCurrentUi();
        List<Action> actions = actionFactory.getActionsFromWidgets(widgets);
        State state = new State(appPackage, curActivity,widgets,actions);
        return state;

    }

    public DFSState createDFSState(){
        String curActivity = device.getCurrentActivity();
        String appPackage = device.getCurrentPackageName();
        List<Widget> widgets = GrabCurrentUi();
        List<Action> actions = actionFactory.getDFSActionsFromWidgets(widgets);
        DFSState dfsState= new DFSState(appPackage, curActivity,widgets,actions);
        return dfsState;
    }

    private List<Widget> GrabCurrentUi() {
        File dumpFile = device.dumpUI();
        try {
            List<Widget> list = DumpService.getNodes(dumpFile);
            return list;
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }
}
