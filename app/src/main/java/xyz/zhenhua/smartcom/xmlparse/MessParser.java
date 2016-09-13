package xyz.zhenhua.smartcom.xmlparse;

import android.util.Xml;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import xyz.zhenhua.smartcom.entity.Mess;

/**
 * Created by zachary on 16/9/13.
 */

public class MessParser {
    public static Mess parseMess(String data) throws XmlPullParserException, IOException, ParserConfigurationException, SAXException {
        InputStream is = new ByteArrayInputStream(data.getBytes());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //取得DocumentBuilderFactory实例
        DocumentBuilder builder = factory.newDocumentBuilder(); //从factory获取DocumentBuilder实例
        Document doc = builder.parse(is);   //解析输入流 得到Document实例
        Element rootElement = doc.getDocumentElement();
        NodeList items = rootElement.getElementsByTagName("MESS");
        NodeList properties = rootElement.getChildNodes();
        Mess mess = new Mess();
        for (int i = 0; i < properties.getLength(); i++) {
            Node property = properties.item(i);
            String nodeName = property.getNodeName();
            if (nodeName.equals("username")) {
                mess.setUsername(property.getFirstChild().getNodeValue());
            } else if (nodeName.equals("title")) {
                mess.setTitle(property.getFirstChild().getNodeValue());
            } else if (nodeName.equals("buf")) {
                mess.setBuf(property.getFirstChild().getNodeValue());
            } else if (nodeName.equals("north")) {
                mess.setNorth(Double.parseDouble(property.getFirstChild().getNodeValue()));
            } else if ((nodeName.equals("east"))) {
                mess.setEast(Double.parseDouble(property.getFirstChild().getNodeValue()));
            } else if (nodeName.equals("datetime")) {
                mess.setDatetime(property.getFirstChild().getNodeValue());
            }
        }
        return mess;
    }
}

//        for (int i = 0; i < items.getLength(); i++) {
//            Book book = new Book();
//            Node item = items.item(i);
//            NodeList properties = item.getChildNodes();
//            for (int j = 0; j < properties.getLength(); j++) {
//                Node property = properties.item(j);
//                String nodeName = property.getNodeName();
//                if (nodeName.equals("id")) {
//                    book.setId(Integer.parseInt(property.getFirstChild().getNodeValue()));
//                } else if (nodeName.equals("name")) {
//                    book.setName(property.getFirstChild().getNodeValue());
//                } else if (nodeName.equals("price")) {
//                    book.setPrice(Float.parseFloat(property.getFirstChild().getNodeValue()));
//                }
//            }
//            books.add(book);
//        }add





