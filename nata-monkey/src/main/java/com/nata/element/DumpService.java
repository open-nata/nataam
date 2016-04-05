package com.nata.element;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-13 17:28
 */
public class DumpService {
    public static List<Widget> getNodes(File file) throws DocumentException, SAXException, FileNotFoundException, UnsupportedEncodingException {
        InputStream ifile = new FileInputStream(file.getAbsolutePath());
        InputStreamReader ir = new InputStreamReader(ifile, "UTF-8");
        SAXReader reader = new SAXReader();
        Document document = reader.read(ir);
        List<Widget> widgetList = new ArrayList<>();
        treeWalk(document, widgetList);
        return widgetList;
    }


    private static void treeWalk(Document document, List<Widget> list) {
        treeWalk(document.getRootElement(), list);
    }

    private static void treeWalk(Element element, List<Widget> list) {
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
                    Widget widget = new Widget();
                    widget.setText(cur.attributeValue("text"));
                    widget.setResourceId(cur.attributeValue("resource-id"));
                    widget.setClassName(cur.attributeValue("class"));
                    widget.setPackageName(cur.attributeValue("package"));
                    widget.setContentDesc(cur.attributeValue("content-desc"));
                    widget.setCheckable(cur.attributeValue("checkable"));
                    widget.setChecked(cur.attributeValue("checked"));
                    widget.setClickable(cur.attributeValue("clickable"));
                    widget.setEnabled(cur.attributeValue("enabled"));
                    widget.setFocusable(cur.attributeValue("focusable"));
                    widget.setFocused(cur.attributeValue("focused"));
                    widget.setScrollable(cur.attributeValue("scrollable"));
                    widget.setLong_clickable(cur.attributeValue("long-clickable"));
                    widget.setPassword(cur.attributeValue("password"));
                    widget.setSelected(cur.attributeValue("selected"));
                    widget.setBounds(cur.attributeValue("bounds"));
                    list.add(widget);
                }
                treeWalk(cur, list);
            }
        }
    }

    public static void main(String[] args) throws SAXException, UnsupportedEncodingException, DocumentException, FileNotFoundException {
        String tempDir = System.getProperty("user.dir");
        String tempDumpfile = tempDir + "/dumps/dumpfile.xml";
        File dumpFile = new File(tempDumpfile);
        List<Widget> list = DumpService.getNodes(dumpFile);
        for (Widget widget:list
             ) {
           System.out.println(widget);
        }
    }

}
