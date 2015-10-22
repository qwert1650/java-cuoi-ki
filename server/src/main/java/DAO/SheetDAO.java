/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Sheet;
import DTO.User;
import helpers.Constants;
import helpers.XMLHelper;
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
    
    public static boolean insertValue(String filenames, String content) throws Exception{
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
    
    public static void insert_sheet(String username, List<String> namesheet) throws Exception{
        User user = UserDAO.getUserByUsername(username);
        List<String> sheets = user.getAccessibleSheets();
        sheets.add(namesheet.get(namesheet.size()-1)); // Add sheet
        user.setAccessibleSheets(sheets);
        updateUser(user);
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.newDocument();
        // root element
        Element rootElement = doc.createElement("files");
        doc.appendChild(rootElement);
        transformDocumentToXml(doc, "content_sheet/"+ namesheet.get(namesheet.size()-1));
        transformDocumentToXml(doc, "online/"+ namesheet.get(namesheet.size()-1));
        transformDocumentToXml(doc, "history/"+ namesheet.get(namesheet.size()-1));
    }
    public static void add_friend(String username, List<String> namesheet) throws Exception{
        User user = UserDAO.getUserByUsername(username);
        List<String> sheets = user.getAccessibleSheets();
        sheets.add(namesheet.get(namesheet.size()-1)); // Add sheet
        user.setAccessibleSheets(sheets);
        updateUser(user);
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
    
    public static void updateUser(User userToUpdate) throws Exception{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(Constants.USER_DATA_FILE_PATH);
        
        NodeList nodeList = document.getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node user = nodeList.item(i);
            if (user.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) user;
                String userName = userElement.getElementsByTagName("username").item(0).getTextContent();
                if(userName.equalsIgnoreCase(userToUpdate.getUserName())){
                    Node oldAccessibleSheetsNode = userElement.getElementsByTagName("accessibleSheets").item(0);
                    userElement.removeChild(oldAccessibleSheetsNode);
                    
                    // Add new accessibleSheetsNode
                    Element newAccessibleSheets = document.createElement("accessibleSheets");
                    for (String sheet: userToUpdate.getAccessibleSheets()){
                        Element sheet1 = document.createElement("sheet");
                        sheet1.setAttribute("filename", sheet);
                        newAccessibleSheets.appendChild(sheet1);
                    }
                    userElement.appendChild(newAccessibleSheets);
                    transformDocumentToXml(document, Constants.USER_DATA_FILE_PATH);
                    System.out.println("updated user");
                    return;
                }
            }
        }
    }
}
