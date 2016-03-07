package com.nata.element;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 17:28
 */
public class DumpService {
    public static List<UINode> getNodes(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        List<UINode> uiNodeList = new ArrayList<>();
        treeWalk(document, uiNodeList);
        return uiNodeList;
    }


    private static void treeWalk(Document document, List<UINode> list) {
        treeWalk(document.getRootElement(), list);
    }

    private static void treeWalk(Element element, List<UINode> list) {
        for (int i = 0, size = element.nodeCount(); i < size; i++) {
            Node node = element.node(i);
            if (node instanceof Element) {
                Element cur = (Element) node;
                if (cur.getName().equals("node")) {

                    /*
                    sample node:
                     <node index="0" text="" resource-id="" class="android.widget.FrameLayout"
                      package="com.chuanwg.chuanwugong" content-desc="" checkable="false" checked="false"
                      clickable="false" enabled="true" focusable="false" focused="false" scrollable="false"
                     long-clickable="false" password="false" selected="false" bounds="[0,0][1080,1920]">
                     */
                    UINode uiNode = new UINode();
                    uiNode.setText(cur.attributeValue("text"));
                    uiNode.setResourceId(cur.attributeValue("resource-id"));
                    uiNode.setClassName(cur.attributeValue("class"));
                    uiNode.setPackageName(cur.attributeValue("package"));
                    uiNode.setContentDesc(cur.attributeValue("content-desc"));
                    uiNode.setCheckable(cur.attributeValue("checkable"));
                    uiNode.setChecked(cur.attributeValue("checked"));
                    uiNode.setClickable(cur.attributeValue("clickable"));
                    uiNode.setEnabled(cur.attributeValue("enabled"));
                    uiNode.setFocusable(cur.attributeValue("focusable"));
                    uiNode.setFocused(cur.attributeValue("focused"));
                    uiNode.setScrollable(cur.attributeValue("scrollable"));
                    uiNode.setLong_clickable(cur.attributeValue("long-clickable"));
                    uiNode.setPassword(cur.attributeValue("password"));
                    uiNode.setSelected(cur.attributeValue("selected"));
                    uiNode.setBounds(cur.attributeValue("bounds"));
                    list.add(uiNode);
                }
                treeWalk(cur, list);
            }
        }
    }

}
