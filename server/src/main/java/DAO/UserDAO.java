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

    public static void addUser(User userToAdd) throws Exception {
        XMLHelper.addUser(userToAdd);
    }

    public static void updateUser(User userToUpdate) throws Exception {
        XMLHelper.updateUser(userToUpdate);
    }

    public static void removeUser(String username) throws Exception {
        XMLHelper.removeUser(username);
    }
}
