/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.List;

/**
 *
 * @author admin
 */
public class Sheet {
    private String column;
    private String row;
    private String Content;

    public Sheet(String column, String row, String Content) {
        this.column = column;
        this.row = row;
        this.Content = Content;
    }
    
    public Sheet() {}

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
}
