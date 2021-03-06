/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import XuLy.MyRenderer;
import XuLy.SendData;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class FormMain extends javax.swing.JFrame {
    String nameFile;
    private DefaultTableModel model = new DefaultTableModel();
    String [][]arr_table = new String[40][15];
    String user;
    String sheet;
    
    public FormMain(String sheet){
        this.sheet = sheet;
        initComponents();
        for(int i = 0; i < 29 ; i++){
            for(int  j = 0 ; j < 9 ; j++)
            {
                arr_table[i][j]="";
            }
        }
        String []sheetfile = sheet.split(";");
        user = sheetfile[1];
        MyRenderer myRenderer = new MyRenderer();
        table_main.setDefaultRenderer(Object.class, myRenderer);
        nameFile = sheetfile[2];
        for(int i = 3; i<sheetfile.length;i++){
            String []sheetrow =  sheetfile[i].split("-");
            for(int j = 0 ; j < sheetrow.length; j++){
                String []sheetcol = sheetrow[j].split("/");
                arr_table[Integer.parseInt(sheetcol[1])][Integer.parseInt(sheetcol[0])] = sheetcol[2];
            }
        }
        //tao du lieu table
        model.addColumn("A");
        model.addColumn("B");
        model.addColumn("C");
        model.addColumn("D");
        model.addColumn("E");
        model.addColumn("F");
        model.addColumn("G");
        model.addColumn("K");
        model.addColumn("I");
        for(int i = 0 ; i < 29 ; i ++){
            model.addRow(new Object[]{arr_table[i][0],arr_table[i][1],arr_table[i][2],arr_table[i][3],arr_table[i][4],arr_table[i][5],arr_table[i][6],arr_table[i][7],arr_table[i][8]});
        }
        table_main.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if(e.getType() == TableModelEvent.UPDATE)
                    cellChanged(e.getFirstRow(), e.getColumn());
            }
        });
        btn_adduser.setEnabled(false);
    }
    public FormMain() {
        initComponents();
    }
    
    public void updatecell(String content){
        String []temp=content.split(";");
        String []item = temp[2].split("/");
        int row = Integer.parseInt(item[1]);
        int col = Integer.parseInt(item[0]);
        if(item.length > 0){
            arr_table[row][col]=item[2];
            model.setRowCount(0);
        for(int i = 0 ; i < 29 ; i ++){
            model.addRow(new Object[]{arr_table[i][0],arr_table[i][1],arr_table[i][2],arr_table[i][3],arr_table[i][4],arr_table[i][5],arr_table[i][6],arr_table[i][7],arr_table[i][8]});
        }
            table_main.repaint();
        }
    }
    
    public void cellChanged(int row, int column)
    {
        String values = table_main.getModel().getValueAt(row, column).toString();
        SendData sd = new SendData(9876,"updateCell;"+user+";"+ nameFile +";"+column+"/"+row+"/"+values);
        sd.start();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_main = new javax.swing.JTable(model);
        jPanel1 = new javax.swing.JPanel();
        btn_adduser = new javax.swing.JButton();
        txt_adduser = new javax.swing.JTextField();
        btn_history = new javax.swing.JButton();
        btn_close = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        jMenu3.setText("jMenu3");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenu4.setText("jMenu4");

        jScrollPane1.setViewportView(table_main);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Mời tham gia"));

        btn_adduser.setText("Mời");
        btn_adduser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_adduserActionPerformed(evt);
            }
        });

        txt_adduser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_adduserKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_adduser, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_adduser)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_adduser)
                    .addComponent(txt_adduser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        btn_history.setText("Lịch Sử");
        btn_history.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_historyActionPerformed(evt);
            }
        });

        btn_close.setText("Close");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_history)
                .addGap(14, 14, 14)
                .addComponent(btn_close)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_history, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_close, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_adduserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adduserActionPerformed
        // TODO add your handling code here:
        SendData senddt = new SendData(9876, "addfriend;"+txt_adduser.getText()+";"+ nameFile);
        senddt.start();
        try {
            senddt.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Manager_Sheet.class.getName()).log(Level.SEVERE, null, ex);
        }
        btn_adduser.setEnabled(false);
        txt_adduser.setText("");
    }//GEN-LAST:event_btn_adduserActionPerformed

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_historyActionPerformed
        // TODO add your handling code here:
        SendData senddt = new SendData(9876,"fullhistory;"+user+";"+ nameFile);
        senddt.start();
        try {
            senddt.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_historyActionPerformed

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        // TODO add your handling code here:
        SendData senddt = new SendData(9876, "closeapp;"+user+";"+ nameFile);
        senddt.start();
        this.setVisible(false);
    }//GEN-LAST:event_btn_closeActionPerformed

    private void txt_adduserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_adduserKeyPressed
        // TODO add your handling code here:
        btn_adduser.setEnabled(true);
    }//GEN-LAST:event_txt_adduserKeyPressed
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormMain().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_adduser;
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_history;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_main;
    private javax.swing.JTextField txt_adduser;
    // End of variables declaration//GEN-END:variables
    //    private void loadSheetToTable (Sheet sheet){
    //        String[] headers = SheetService.getHeadersFromSheet(sheet);
    //        sheetTableModel = new CommonTableModel(headers, SheetService.convertSheetToArrayToFillTable(sheet));
    //        table_main.setModel(sheetTableModel);
//    }
}
