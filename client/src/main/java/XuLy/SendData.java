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
public class SendData extends Thread{
    String sentence;
    int port;
    public SendData(int port, String sentence){
        this.sentence = sentence;
        this.port = port;
    }
    public void run(){
        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
            clientSocket.setSoTimeout(300);
        } catch (SocketException ex) {
            Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
        }
        InetAddress IPAddress = null;
        try {
            IPAddress = InetAddress.getByName("192.168.10.3");//Ip cua may nhan
        } catch (UnknownHostException ex) {
            Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] Data = new byte[1024];
        Data = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(Data, Data.length, IPAddress, port);
        try {
            clientSocket.send(sendPacket);
        } catch (IOException ex) {
            Logger.getLogger(SendData.class.getName()).log(Level.SEVERE, null, ex);
        }
        clientSocket.close();
    }
}
