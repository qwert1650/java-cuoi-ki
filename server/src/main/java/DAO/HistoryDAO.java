/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author admin
 */
public class HistoryDAO {
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
