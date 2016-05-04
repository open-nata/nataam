package com.nata.server;

import com.nata.AdbDevice;
import com.nata.action.Action;
import com.nata.action.ActionFactory;
import com.nata.element.DumpService;
import com.nata.element.Widget;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-05-03 17:09
 */
public class ActionMaker {
    public static String  getActions() {
        AdbDevice device = new AdbDevice();
        File dumpFile = device.dumpUI();
        try {
            List<Widget> list = DumpService.getNodes(dumpFile);
            ActionFactory factory = new ActionFactory(device);
            List<Action> actions = factory.getUIActionsFromWidgets(list);

            List<String> actionList = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (Action action : actions) {
                sb.append(action.toCommand() +"\n");
            }
            return sb.toString();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
            System.out.println(ActionMaker.getActions());
    }

}
