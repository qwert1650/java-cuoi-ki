/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XuLy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class send_Client extends Thread{
    String sentence;
    String id;
    public send_Client(String sentence, String id){
        this.sentence = sentence;
        this.id = id;
    }
    public void run(){
        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
            clientSocket.setSoTimeout(300);
        } catch (SocketException ex) {
            Logger.getLogger(send_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        InetAddress IPAddress = null;
        try {
            IPAddress = InetAddress.getByName(id);//Ip cua may nhan
        } catch (UnknownHostException ex) {
            Logger.getLogger(send_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] Data = new byte[1024];
        Data = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(Data, Data.length, IPAddress,9876);
        try {
            clientSocket.send(sendPacket);
        } catch (IOException ex) {
            Logger.getLogger(send_Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        clientSocket.close();
    }
}
