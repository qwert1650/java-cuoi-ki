/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XuLy;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author admin
 */
public class MyRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!hasFocus) {
            c.setBackground(new java.awt.Color(255, 255, 255)); // backgroud.
            setBorder(BorderFactory.createLineBorder(Color.yellow));
        } else {
            c.setBackground(new java.awt.Color(20, 255, 20));
            setBorder(BorderFactory.createLineBorder(Color.RED));
        }
        return c;
    }
}