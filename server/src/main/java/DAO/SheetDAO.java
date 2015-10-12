/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Sheet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
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
public class SheetDAO {
    public static List<Sheet> getSheetByUsername(String namesheet) {
        List<Sheet> sheet = getSheetFromXML("content_sheet/"+namesheet);
        return sheet;
    }
    
    public static boolean insertvalue(String filenames, String content) throws Exception{
        String []temp = content.split("/");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("content_sheet/"+filenames);
        Element root = document.getDocumentElement();
        Element sheetElement = document.createElement("record");
        
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
        transformDocumentToXml(document, "content_sheet/"+filenames);
        return true;
    }
    
    private static void transformDocumentToXml(Document document, String xmlFilePath) throws Exception{
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(xmlFilePath);
        transformer.transform(source, result);
    }
    
    public static List<Sheet> getSheetFromXML(String filePath) {
        List<Sheet> sheet = new ArrayList<Sheet>();
        NodeList nodeList = getElementsByTagNameFromXml(filePath, "record");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Sheet content_sheet = getSheetFromNode(node);
                sheet.add(content_sheet);
            }
        }
        return sheet;
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
    
    public static Sheet getSheetFromNode(Node node){
        Sheet sheet = new Sheet();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String col = element.getElementsByTagName("column").item(0).getTextContent();
            String row = element.getElementsByTagName("row").item(0).getTextContent();
            String content = element.getElementsByTagName("content").item(0).getTextContent();
            sheet.setColumn(col);
            sheet.setRow(row);
            sheet.setContent(content);
        }
        return sheet;
    }
    public static void addNodeToXML(String nameIn, String portIn) throws ParserConfigurationException
    {
        //        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //        DocumentBuilder builder = dbf.newDocumentBuilder();
        //        Document existingdoc = builder.parse("content_sheet/"+namesheet);
        //        Element root = doc.createElement("Objects");
        //        doc.appendChild(root);
        //        Node copy = doc.importNode(existingdoc.getDocumentElement(), true);
        //        root.appendChild(copy);
    }
}
