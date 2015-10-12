package DAO;



import DTO.User;
import helpers.XMLHelper;
import java.util.List;

public class UserDAO {

    public static List<User> getUsers() {
        return XMLHelper.getUsersFromXML("user/user-data.xml");
    }

    public static User getUserByUsername(String username) {
        List<User> users = getUsers();
        for(User user: users){
            if(user.getUserName().equalsIgnoreCase(username))
                return user;
        }
        return null;
    }

    public static void addUser(User userToAdd) {
        //TODO: chua xong
    }

    public static void removeUser(String username) {
        //TODO: chua xong
    }
}
