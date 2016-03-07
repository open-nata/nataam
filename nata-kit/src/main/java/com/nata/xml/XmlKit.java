package com.nata.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-01-21 16:47
 */
public class XmlKit {

    public void read() throws DocumentException {
        SAXReader reader = new SAXReader();
        InputStream is = XmlKit.class.getClassLoader().getResourceAsStream("window_dump.xml");
        Document document = reader.read(is);
//        treeWalk(document);
//        Element root = document.getRootElement();

        // iterate through child elements of root
//        for (Iterator i = root.elementIterator(); i.hasNext(); ) {
//            Element element = (Element) i.next();
//            System.out.println("name : " + element.getName());
//
//        }

//        List<Element> childElements = root.elements();
//        for (Element child : childElements) {
//
//
//            //已知属性名情况下
//            System.out.println("id: " + child.attributeValue("id"));
//
//            //未知子元素名情况下
//            /*List<Element> elementList = child.elements();
//            for (Element ele : elementList) {
//                System.out.println(ele.getName() + ": " + ele.getText());
//            }
//            System.out.println();*/
//
//            //已知子元素名的情况下
//            System.out.println("title" + child.elementText("title"));
//            System.out.println("author" + child.elementText("author"));
//            //这行是为了格式化美观而存在
//            System.out.println();
//        }
    }

//    public void treeWalk(Document document) {
//        treeWalk( document.getRootElement() );
//    }
//
//    public void treeWalk(Element element) {
//        for ( int i = 0, size = element.nodeCount(); i < size; i++ ) {
//            Node node = element.node(i);
//            if ( node instanceof Element )  {
//                if(node.getName().equals("node")){
//                    System.out.println("name : " + node.getName());
//                }
//                treeWalk( (Element) node );
//            }
//        }
//    }

    public static void main(String[] args) {
        XmlKit xmlKit = new XmlKit();
        try {
            xmlKit.read();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
