package helpers;

import DTO.User;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;

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

    public static User getUserFromNode(Node node){
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

    private static List<String> getAccessibleSheetsFromNode(Node accessibleSheetsNode){
        List<String> sheets = new ArrayList<String>();
        Element element = (Element)accessibleSheetsNode;
        NodeList nodeList = element.getElementsByTagName("sheet");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String fileName = ((Element)node).getAttribute("filename");
//            Sheet sheet = SheetService.createSheetFromCsv(CURRENT_DIRECTORY + FILE_SEPARATOR + fileName);
            sheets.add(fileName);
        }
        return sheets;
    }
}
