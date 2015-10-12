/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XuLy;

import DAO.OnlineDAO;
import DAO.SheetDAO;
import DAO.UserDAO;
import DTO.Online;
import DTO.Sheet;
import DTO.User;
import helpers.XMLHelper;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class Recive_center  extends Thread{
    UserDAO us = new UserDAO();
    boolean fag = true;
    public void run(){
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(9876);
        } catch (SocketException ex) {
            Logger.getLogger(Recive_center.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(fag == true)
        {
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException ex) {
                Logger.getLogger(Recive_center.class.getName()).log(Level.SEVERE, null, ex);
            }
            String sentence = new String( receivePacket.getData());
            sentence = sentence.trim();
            InetAddress IPAddress = receivePacket.getAddress();
            String temp[];
            temp=sentence.split(";");
            //kiem tra dang nhap
            if(temp[0].equals("loggin")){
                //neu dang nhap thanh cong
                User u= us.getUserByUsername(temp[1]);
                if(u != null ){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Recive_center.class.getName()).log(Level.SEVERE, null, ex);
                    }                  
                    String query = "Loggin;"+temp[1];
                    for(String item:u.getAccessibleSheets()){
                        query = query+";"+item;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Recive_center.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    send_Client seCl= new send_Client(query, IPAddress.getHostAddress());
                    seCl.start();
                    try {
                        seCl.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Recive_center.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Recive_center.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    send_Client seCl= new send_Client("Fail;0", IPAddress.getHostAddress());
                    seCl.start();
                    try {
                        seCl.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Recive_center.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }//xu li doc file sheet
            else if(temp[0].equals("readfire")){
                String query="updateContent;"+temp[1]+";"+temp[2]+";";
                List<Sheet> sheet = SheetDAO.getSheetByUsername(temp[2]);
                for(Sheet item : sheet){
                    query = query + item.getColumn()+"/"+item.getRow()+"/"+item.getContent()+"-";
                }
                try {
                    OnlineDAO.insertvalue(temp[2],temp[1],IPAddress.getHostAddress());
                } catch (Exception ex) {
                    Logger.getLogger(Recive_center.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                send_Client sc = new send_Client(query, IPAddress.getHostAddress());
                sc.start();
            }//cap nhat noi dung cell
            else if(temp[0].equals("updateCell")){
                List<Online> list = new ArrayList<Online>() {};
                try {             
                    if(SheetDAO.insertvalue(temp[2], temp[3])==true)
                    {
                        list = OnlineDAO.getIpFromXML("online/"+temp[2]);
                         for(Online item : list){
//                            Thread.sleep(100);
                            send_Client sc = new send_Client("updatelientuc;"+temp[1]+";"+temp[3], item.getIp());
                            sc.start();
                            sc.join();
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Recive_center.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
        }
    }
}
