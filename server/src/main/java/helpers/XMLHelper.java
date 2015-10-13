package helpers;

import DAO.UserDAO;
import DTO.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLHelper {

    public static List<User> getUsersFromXML(String filePath) {
        List<User> users = new ArrayList<User>();
        NodeList nodeList = getElementsByTagNameFromXml(filePath, "user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                User user = getUserFromNode(node);
                users.add(user);
            }
        }
        return users;
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

    public static User getUserFromNode(Node node) {
        User user = new User();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String userName = element.getElementsByTagName("username").item(0).getTextContent();
            String password = element.getElementsByTagName("password").item(0).getTextContent();
            NodeList accessibleSheets = element.getElementsByTagName("accessibleSheets");
            List<String> sheets = getAccessibleSheetsFromNode(accessibleSheets.item(0));
            user.setUserName(userName);
            user.setPassword(password);
            user.setAccessibleSheets(sheets);
        }
        return user;
    }

    private static List<String> getAccessibleSheetsFromNode(Node accessibleSheetsNode) {
        List<String> sheets = new ArrayList<String>();
        Element element = (Element) accessibleSheetsNode;
        NodeList nodeList = element.getElementsByTagName("sheet");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String fileName = ((Element) node).getAttribute("filename");
            sheets.add(fileName);
        }
        return sheets;
    }

    public static void addUser(User userToAdd) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(Constants.USER_DATA_FILE_PATH);
        Element root = document.getDocumentElement();
        Element userElement = document.createElement("user");

        Element usernameElement = document.createElement("username");
        usernameElement.appendChild(document.createTextNode(userToAdd.getUserName()));
        userElement.appendChild(usernameElement);

        Element passwordElement = document.createElement("password");
        passwordElement.appendChild(document.createTextNode(userToAdd.getPassword()));
        userElement.appendChild(passwordElement);

        Element accessibleSheets = document.createElement("accessibleSheets");
        for (String sheet: userToAdd.getAccessibleSheets()){
            Element sheet1 = document.createElement("sheet");
            sheet1.setAttribute("filename", sheet);
            accessibleSheets.appendChild(sheet1);
        }
        userElement.appendChild(accessibleSheets);
        root.appendChild(userElement);
        transformDocumentToXml(document, Constants.USER_DATA_FILE_PATH);
        System.out.println("added user");
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
                    // Update password
                    Node passwordNode = userElement.getElementsByTagName("password").item(0);
                    passwordNode.setTextContent(userToUpdate.getPassword());

                    // Remove old accessibleSheetsNode
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

    public static void removeUser(String userNameToRemove) throws Exception{

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(Constants.USER_DATA_FILE_PATH);

        NodeList nodeList = document.getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node user = nodeList.item(i);
            if (user.getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) user;
                String userName = userElement.getElementsByTagName("username").item(0).getTextContent();
                if(userName.equalsIgnoreCase(userNameToRemove)){
                    userElement.getParentNode().removeChild(userElement);
                    transformDocumentToXml(document, Constants.USER_DATA_FILE_PATH);
                    System.out.println("removed user");
                    return;
                }
            }
        }
    }

    private static void transformDocumentToXml(Document document, String xmlFilePath) throws Exception{
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(xmlFilePath);
        transformer.transform(source, result);
    }

    public static void addSheetToUser(String username, String sheetName) throws Exception{
        User user = UserDAO.getUserByUsername(username);
        List<String> sheets = user.getAccessibleSheets();
        sheets.add(sheetName); // Add sheet
        user.setAccessibleSheets(sheets);
        updateUser(user);
    }
}
