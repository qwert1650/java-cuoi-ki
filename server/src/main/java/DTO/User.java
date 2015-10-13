package DTO;

import helpers.XMLHelper;

import java.util.List;

public class User {

    private String userName;
    private String password;
    private List<String> accessibleSheets;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAccessibleSheets() {
        return accessibleSheets;
    }

    public void setAccessibleSheets(List<String> accessibleSheets) {
        this.accessibleSheets = accessibleSheets;
    }

    public void addSheetToAccessibleSheets(String sheetName) throws Exception{
        XMLHelper.addSheetToUser(this.getUserName(), sheetName);
    }

    public User(){

    }
}
