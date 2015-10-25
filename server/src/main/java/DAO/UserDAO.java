package DAO;



import DTO.User;
import helpers.XMLHelper;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UserDAO {
    
    public static List<User> getUsers() {
        return XMLHelper.getUsersFromXML("user/user-data.xml");
    }
    
    public static User getUserByUsername(String username) {
        List<User> users = getUsers();
        for(User user: users){
            if(user.getUserName().equalsIgnoreCase(username))
                return user;
        }
        return null;
    }
    
    public static User getLoggin(String username, String pass) {
        List<User> users = getUsers();
        for(User user: users){
            if(user.getUserName().equalsIgnoreCase(username) && user.getPassword().equals(pass))
                return user;
        }
        return null;
    }
    public static boolean createuser(String username, String password) throws Exception{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("user/user-data.xml");
        Element root = document.getDocumentElement();
        Element sheetElement = document.createElement("user");
        
        Element columneElement = document.createElement("username");
        columneElement.appendChild(document.createTextNode(username));
        sheetElement.appendChild(columneElement);
        
        Element rowElement = document.createElement("password");
        rowElement.appendChild(document.createTextNode(password));
        sheetElement.appendChild(rowElement);
        
        Element contentElement = document.createElement("accessibleSheets");
        //contentElement.appendChild(document.createTextNode(temp[2]));
        sheetElement.appendChild(contentElement);
        
        root.appendChild(sheetElement);
        transformDocumentToXml(document,"user/user-data.xml");
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
