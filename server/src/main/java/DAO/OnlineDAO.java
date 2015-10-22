/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Online;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author admin
 */
public class OnlineDAO {
    public static boolean insertvalue(String filenames,String nameuser, String ip) throws Exception{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("online/"+filenames);
        Element root = document.getDocumentElement();
        Element sheetElement = document.createElement("users");
        
        Element columneElement = document.createElement("username");
        columneElement.appendChild(document.createTextNode(nameuser));
        sheetElement.appendChild(columneElement);
        
        Element rowElement = document.createElement("ip");
        rowElement.appendChild(document.createTextNode(ip));
        sheetElement.appendChild(rowElement);
        root.appendChild(sheetElement);
        transformDocumentToXml(document, "online/"+filenames);
        return true;
    }
    
    public static void updateOnline(String ip, String file) throws Exception{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("online/"+file);
        document.getDocumentElement().normalize();
        NodeList nodeList = document.getElementsByTagName("users");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element)node;
                String userName = userElement.getElementsByTagName("ip").item(0).getTextContent();
                if(userName.equalsIgnoreCase(ip)){
                    node.getParentNode().removeChild(node);
                }
            }
        }
        transformDocumentToXml(document, "online/"+file);
        return;
    }
    
    private static void transformDocumentToXml(Document document, String xmlFilePath) throws Exception{
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(xmlFilePath);
        transformer.transform(source, result);
    }
    
    public static List<Online> getIpFromXML(String filePath) {
        List<Online> list = new ArrayList<Online>();
        NodeList nodeList = getElementsByTagNameFromXml(filePath, "users");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Online user = getUserFromNode(node);
                list.add(user);
            }
        }
        return list;
    }
    
    public static NodeList getElementsByTagNameFromXml(String filePath, String tagName) {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            return doc.getElementsByTagName(tagName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Online getUserFromNode(Node node){
        Online online = new Online();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String userName = element.getElementsByTagName("username").item(0).getTextContent();
            String ip = element.getElementsByTagName("ip").item(0).getTextContent();
            online.setUsername(userName);
            online.setIp(ip);
        }
        return online;
    }
}
