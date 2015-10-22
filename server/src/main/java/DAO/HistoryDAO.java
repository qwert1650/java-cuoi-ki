/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.History;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class HistoryDAO {
    public static List<History> getHistory(String namesheet) {
        List<History> his = getSheetFromXML("history/"+namesheet);
        return his;
    }
    
    public static List<History> getSheetFromXML(String filePath) {
        List<History> hist = new ArrayList<History>();
        NodeList nodeList = getElementsByTagNameFromXml(filePath, "history");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                History content_his = getSheetFromNode(node);
                hist.add(content_his);
            }
        }
        return hist;
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
    
    public static History getSheetFromNode(Node node){
        History his = new History();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String username = element.getElementsByTagName("username").item(0).getTextContent();
            String date = element.getElementsByTagName("date").item(0).getTextContent();
            String column = element.getElementsByTagName("column").item(0).getTextContent();
            String row = element.getElementsByTagName("row").item(0).getTextContent();
            String content = element.getElementsByTagName("content").item(0).getTextContent();
            his.setUsername(username);
            his.setDate(date);
            his.setColumn(column);
            his.setRow(row);
            his.setContent(content);
        }
        return his;
    }
    
    
    public static boolean insertvalue(String filenames,String nameuser, String content) throws Exception{
        String []temp = content.split("/");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("history/"+filenames);
        Element root = document.getDocumentElement();
        Element sheetElement = document.createElement("history");
        
        Element usernameElement = document.createElement("username");
        usernameElement.appendChild(document.createTextNode(nameuser));
        sheetElement.appendChild(usernameElement);
        
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy-HH-mm-ss");
        Date date = new Date();
        String thoigian = dateFormat.format(date).toString();
        
        Element dateElement = document.createElement("date");
        dateElement.appendChild(document.createTextNode(thoigian));
        sheetElement.appendChild(dateElement);
        
        Element columneElement = document.createElement("column");
        columneElement.appendChild(document.createTextNode(temp[0]));
        sheetElement.appendChild(columneElement);
        
        Element rowElement = document.createElement("row");
        rowElement.appendChild(document.createTextNode(temp[1]));
        sheetElement.appendChild(rowElement);
        
        Element contentElement = document.createElement("content");
        contentElement.appendChild(document.createTextNode(temp[2]));
        sheetElement.appendChild(contentElement);
        
        root.appendChild(sheetElement);
        transformDocumentToXml(document, "history/"+filenames);
        return true;
    }
    
    private static void transformDocumentToXml(Document document, String xmlFilePath) throws Exception{
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(xmlFilePath);
        transformer.transform(source, result);
    }
}
