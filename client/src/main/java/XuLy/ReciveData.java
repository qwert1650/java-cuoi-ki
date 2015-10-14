/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XuLy;

import Forms.FormMain;
import Forms.Loggin;
import Forms.Manager_Sheet;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class ReciveData extends Thread{
    boolean fag = true;
    boolean kq = false;
    public boolean result(){
        return kq;
    }
    Forms.FormMain frmmain;
    public void run(){
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(9876);
        } catch (SocketException ex) {
            Logger.getLogger(ReciveData.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(fag == true)
        {
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException ex) {
                Logger.getLogger(ReciveData.class.getName()).log(Level.SEVERE, null, ex);
            }
            String sentence = new String(receivePacket.getData());
            sentence = sentence.trim();
            InetAddress IPAddress = receivePacket.getAddress();
            String temp[];
            temp=sentence.split(";");
            if(temp[0].equals("Loggin")){
                ArrayList<String> list = new ArrayList<String>();
                for(String item: temp){
                    list.add(item);
                }
                kq = true;
                result();
                Forms.Manager_Sheet frmmanager = new Manager_Sheet(list);
                frmmanager.setVisible(true);
            }
            else if(temp[0].equals("updateContent")){
                kq = true;
                result();
                frmmain = new FormMain(sentence);
                frmmain.setVisible(true);
            }
            else if(temp[0].equals("updatelientuc")){
                frmmain.updatecell(sentence);
            }
        }
    }
}
