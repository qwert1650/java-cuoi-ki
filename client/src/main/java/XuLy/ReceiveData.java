/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XuLy;

import Forms.FormMain;
import Forms.History;
import Forms.Manager_Sheet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class ReceiveData extends Thread{
    boolean fag = true;
    boolean kq = false;
    public boolean result(){
        return kq;
    }
    Forms.FormMain frmmain;
    Forms.History frmhistory;
    Forms.Manager_Sheet frmmanager_sheet;
    public void run(){
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(9876);
        } catch (SocketException ex) {
            Logger.getLogger(ReceiveData.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(fag == true)
        {
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException ex) {
                Logger.getLogger(ReceiveData.class.getName()).log(Level.SEVERE, null, ex);
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
                frmmanager_sheet = new Manager_Sheet(list);
                frmmanager_sheet.setVisible(true);
            }
            else if(temp[0].equals("Fail")){
                JOptionPane.showMessageDialog(null, "Tài khoản ko tồn tại hoặc mật khẩu không đúng!");
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
            else if(temp[0].equals("fullhistory")){
                ArrayList<String> contenthis = new ArrayList<String>();
                String []content = temp[3].split("~");
                for(String item : content){
                    String []contentcol = item.split("/");
                    String noidung = contentcol[0]+"~"+contentcol[1]+"~"+contentcol[2]+"~"+contentcol[3]+"~"+contentcol[4];
                    contenthis.add(noidung);
                }
                frmhistory = new History(contenthis);
                frmhistory.setVisible(true);
            }
            else if(temp[0].equals("updatelistsheet")){
                ArrayList<String> list = new ArrayList<String>();
                for(String item: temp){
                    list.add(item);
                }
                frmmanager_sheet.refreshlistfile(list);
            }
            else if(temp[0].equals("createtrue")){
                JOptionPane.showMessageDialog(null, "Đăng kí thành công!");
            }
            else if(temp[0].equals("addtrue")){
                JOptionPane.showMessageDialog(null, "Mời thành công");
            }
            else if(temp[0].equals("addfalse")){
                JOptionPane.showMessageDialog(null, "Tài khoản không tồn tại!");
            }
            else if(temp[0].equals("createfalse")){
                JOptionPane.showMessageDialog(null, "Tài khoản đã tồn tại. Vui lòng chọn tài khoản khác!");
            }
            else{
                JOptionPane.showMessageDialog(null, "Mất kết nối mạng");
            }
        }
    }
}
