package Dashboard;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.util.ArrayList;

public class XML {
    String str;
    Document xml;

    public XML(String str) { //получаем на вход строку и сразу конвертируем ее в xml документ
        this.str = str;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            //Создаём XML документ из входной строки
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(str)));
            doc.getDocumentElement().normalize();
            this.xml = doc;

        } catch (Exception e) {
            e.printStackTrace();
            //TODO: ДОбавить логирование
        }//try

    }


    public ArrayList<String> getXmlAttributes(String path, String item) {
        ArrayList<String> xmlAttributes = new ArrayList<String>();
        try {

            //Указываем XPath по которому перебирать элементы
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = path;
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xml, XPathConstants.NODESET);

            //Перебираем элементы с именем item
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    xmlAttributes.add(eElement.getAttribute(item));
                }
            }
            return xmlAttributes;

        } catch (Exception e) {
            e.printStackTrace();
//            return null;
            return xmlAttributes;
        }
    }

    public String getFirstXmlAttribute(String path, String item) {
        ArrayList<String> xmlAttributes = new ArrayList<String>();
        xmlAttributes = getXmlAttributes(path,item);
        if (xmlAttributes.size() > 0) {
            return xmlAttributes.get(0);
        } else {
            return null;
        }
    }

    public ArrayList<String> getXmlElements(String path, String item) {
        ArrayList<String> xmlElements = new ArrayList<String>();
        try {

            //Указываем XPath по которому перебирать элементы
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = path;
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xml, XPathConstants.NODESET);

            //Перебираем элементы с именем item
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getElementsByTagName(item).getLength() > 0) {
                        xmlElements.add(eElement.getElementsByTagName(item).item(0).getTextContent());
                    }
                }
            }
            return xmlElements;

        } catch (Exception e) {
            e.printStackTrace();
//            return null;
            return xmlElements;
        }
    }

    public String getFirstXmlElement(String path, String item) {
        ArrayList<String> xmlElements = new ArrayList<String>();
        xmlElements=getXmlElements(path,item);
        if (xmlElements.size() > 0) {
            return xmlElements.get(0);
        } else {
            return null;
        }
    }

}