package Controllers;


/**
 * UserController class handles interface between LoginMenu/CreateAccountMenu
 * views and the User class
 * @author Sebastian Schwagerl, rsking
 * @version 11/25/18
 */

import Models.User;
import java.util.Date;
import java.util.List;


public class UserController {


    /**
     * Logs user in by checking if they exist.
     * @param _username
     * @param _password
     * @return User
     */
    public static User loginUser(String _username, String _password) {
        List<User> userList;

        // Find the user with this username and password pair.
        userList = Models.User.userExists(_username, _password);

        User userInfo = null;
        // If a user is found, store it in the User object and return it.
        if (userList.size() > 0){
            userInfo = userList.get(0);
        }

        return userInfo;
    }


    /**
     * Registering a new account and all of the errors that may occur with
     * faulty inputs before it is sent of to generate an insert.
     * @param _username
     * @param _password
     * @param _repeat
     * @param _fullName
     * @param _email
     * @param _phoneNum
     * @return boolean
     */
    public static boolean registration(String _username, String _password,
            String _repeat, String _fullName, String _email, String _phoneNum) {

        if(!Models.User.validateRegistrationData(_username, _password, _repeat, _email, _phoneNum)) {
            return false;
        }
        // Formats today's date.
        Date date = new Date();
        java.sql.Date timestamp = new java.sql.Date(date.getTime());

        // Inserts the new User into the database.  If an error is encountered, it will return false.
        return Models.User.saveNewUser(_username, _password, _fullName, _email, _phoneNum, timestamp);
    }
}