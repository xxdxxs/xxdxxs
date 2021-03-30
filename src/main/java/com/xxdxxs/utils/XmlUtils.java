package com.xxdxxs.utils;

import org.dom4j.*;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.*;

/**
 * @author xxdxxs
 */
public class XmlUtils {

    /**
     * 提供想要的节点数据的父节点
     *
     * @param xml 字符串
     * @param targetParentNode 目标节点父节点
     * @return List<Map>
     * @throws DocumentException
     */
    public static List<Map<String, String>> fromXml(String xml, String targetParentNode) throws DocumentException {
        String xpath = "//" + targetParentNode;
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        Document document = DocumentHelper.parseText(xml);
        List<Element> elements = document.selectNodes(xpath);
        elements.stream().forEach(element -> {
            Map<String, String> map = new HashMap<>();
            Iterator iterator = element.elementIterator();
            while (iterator.hasNext()) {
                Element childElement = (Element) iterator.next();
                map.put(childElement.getName(), childElement.getText());
            }
            resultList.add(map);
        });
        return resultList;
    }


}
