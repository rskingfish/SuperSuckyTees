package Models;


/**
 * Manages the User and User Data, assisting in Login, determining rights,
 * and storing information for quick checkout if provided.
 * @author Sebastian Schwagerl, rsking
 * @version 11/25/18
 */

import Models.SQL.SQLQueryActions;
import Utility.Tools;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import view.AlertBox;


public class User {

    private static User currentUser;

    private int userID;
    private String fullName;
    private String email;
    private String phoneNum;
    private String cardNum;
    private String cvc;
    private String username;
    private String password;
    private String accessType;
    private Date dateCreated;
    private Date lastAccessed;
    private static SQLQueryActions action = new SQLQueryActions();


    //Full Constructor
    public User(int _userID, String _fullName, String _email, String _phoneNum,
            String _cardNum, String _cvc, String _username, String _password,
            String _accessType, Date _dateCreated, Date _lastAccessed) {

        this.userID = _userID;
        this.fullName = _fullName;
        this.email = _email;
        this.phoneNum = _phoneNum;
        this.cardNum = _cardNum;
        this.cvc = _cvc;
        this.username = _username;
        this.password = _password;
        this.accessType = _accessType;
        this.dateCreated = _dateCreated;
        this.lastAccessed = _lastAccessed;

        currentUser = this;
    }


    //Empty Constructor
    public User(){
    }


    //Partial constructor when existing account login
    public User(String _username, String _password) {

        //Formats today's date.
        Date date = new Date();
        java.sql.Date timestamp = new java.sql.Date(date.getTime());

        this.username = _username;
        this.password = _password;
        this.lastAccessed = timestamp;

        currentUser = this;
    }


    //Partial constructor when new account created
    public User(int _userID, String _username, String _password, String _email) {

        //Formats today's date.
        Date date = new Date();
        java.sql.Date timestamp = new java.sql.Date(date.getTime());

        this.userID = _userID;
        this.username = _username;
        this.password = _password;
        this.email = _email;
        this.dateCreated = this.lastAccessed = timestamp;

        currentUser = this;
    }


    public String getAccessType() {
        return this.accessType;
    }


    public String getCardNum() {
        return this.cardNum;
    }


    public static User getCurrentUser(){
        return currentUser;
    }


    public String getCVC() {
        return this.cvc;
    }


   public Date getDateCreated() {
        return this.dateCreated;
    }


    public String getEmail() {
        return this.email;
    }


    public String getFullName() {
        return this.fullName;
    }


    public int getIDNum() {
        return this.userID;
    }


    public Date getLastAccessed() {
        return this.lastAccessed;
    }


    public String getPassword() {
        return this.password;
    }


    public String getPhoneNum() {
        return this.phoneNum;
    }


    public String getUserName() {
        return this.username;
    }


    public static boolean isLoggedIn(){
        return (currentUser != null);
    }


     public void setAccessType(String _accessType) {
        this.accessType = _accessType;
    }


    public void setCardNum(String _cardNum) {
        this.cardNum = _cardNum;
    }


    public static void setCurrentUser(User _user){
        currentUser = _user;
    }


    public void setCVC(String _cvc) {
        this.cvc = _cvc;
    }


    public void setDateCreated(Date _dateCreated) {
        this.dateCreated = _dateCreated;
    }


    public void setEmail(String _email) {
        this.email = _email;
    }


    public void setFullName(String _fullName) {
        this.fullName = _fullName;
    }


    public void setIDNum(int _idNum) {
        this.userID = _idNum;
    }


    public void setLastAccessed(Date _lastAccessed) {
        this.lastAccessed = _lastAccessed;
    }


    public void setPassword(String _password) {
        this.password = _password;
    }


    public void setPhoneNum(String _phoneNum) {
        this.phoneNum = _phoneNum;
    }


    public void setUserName(String _userName) {
        this.username = _userName;
    }


    /**
     * Inserts the new user into the database.
     * @param _username
     * @param _password
     * @param _fullName
     * @param _email
     * @param _phoneNum
     * @param _dateCreated
     * @return
     */
    public static boolean saveNewUser(String _username, String _password, String _fullName, String _email, String _phoneNum, java.sql.Date _dateCreated) {
		// Map column names and values to be used in INSERT statement in SQL.
        Map<String, String> insertValuePairs = new HashMap<>();

        insertValuePairs.put("username", _username);
        insertValuePairs.put("password", _password);
        insertValuePairs.put("fullName", _fullName);
        insertValuePairs.put("email", _email);
        insertValuePairs.put("phoneNum", _phoneNum);
        insertValuePairs.put("accessType", "Customer");
        insertValuePairs.put("dateCreated", _dateCreated.toString());

        // Run the SQL statement and return the results.
        return action.insertObject("Users", insertValuePairs);
    }


    /**
     * Checks to see if the username is already stored within the database.
     * Prevents identical Username entries.
     * @param _username
     * @return boolean
     */
    public static boolean userExists(String _username) {
		// Setup for SQL SELECT statement.
        String[] selectTables = {"Users"};
        String[] selectColumns = {"username"};
        String[] orderBy = {};

        Map<String, String> insertValuePairs = new HashMap<>();
        insertValuePairs.put("username", _username);

        ResultSet results;
        try {
            results = action.selectObjectBasic(selectTables, selectColumns, insertValuePairs, orderBy);
            return results.next() != false;
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }


    /**
     * Checks to see if the username is stored within the database.
     * Used primarily for Login.
     * @param _username
     * @param _password
     * @return
     */
    public static List<User> userExists(String _username, String _password) {
        // Setup for SQL SELECT statement.
        String[] selectTables = {"users"};
        String[] selectColumns = {"userID", "fullName", "email", "phoneNum", "cardNum", "cvc", "username", "accessType"};
        String[] orderBy = {};

        // Set up the where statement by mapping pairs of tables with values.
        Map<String, String> insertValuePairs = new HashMap<>();
        insertValuePairs.put("username", _username);
        insertValuePairs.put("password", _password);

        try {
            // Store results of SELECT statement in a ResultSet.
            ResultSet results = action.selectObjectBasic(selectTables, selectColumns, insertValuePairs, orderBy);
            List<User> userList = new ArrayList<>();

            // Take results in the ResultSet and fill in a List of addresses.
            while (results.next()) {
                User user = new User();
                user.setIDNum(results.getInt("userID"));
                user.setFullName(results.getString("fullName"));
                user.setEmail(results.getString("email"));
                user.setPhoneNum(results.getString("phoneNum"));
                user.setCardNum(results.getString("cardNum"));
                user.setCVC(results.getString("cvc"));
                user.setUserName(results.getString("username"));
                user.setAccessType(results.getString("accessType"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException ex) {
            AlertBox.display(ex);
            return null;
        }
    }


    /**
     * Method to verify that user provided valid information on registration form
     * @param _username
     * @param _password
     * @param _repeatPassword
     * @param _email
     * @param _phoneNum
     * @return
     */
    public static boolean validateRegistrationData(String _username, String _password,
            String _repeatPassword, String _email, String _phoneNum) {

        //Check if the username or password textboxes are empty.
        if (_username.isEmpty() || _password.isEmpty() || _email.isEmpty()) {
            AlertBox.display(Alert.AlertType.ERROR,
                    "Username, password, and email address are required "
                    + "entries.\nPlease provide this information.",
                    "Missing Information", "Missing Information");
            return false;
        }

        //Check if the password was entered correctly in repeatPassword.
        if (_password.compareTo(_repeatPassword) != 0) {
            AlertBox.display(Alert.AlertType.ERROR,
                    "Your passwords did not match.\n"
                    + "Please make sure to enter correctly.",
                    "Incorrect Information Provided", "Password Mismatch");
            return false;
        }

        //Check to see if the username has already been taken.
        if (Models.User.userExists(_username) == true) {
            AlertBox.display(Alert.AlertType.ERROR,
                    "An account with this username already exists.\n"
                    + "Try a different username.",
                    "Username Already Taken", "Account Username Error");
            return false;
        }

        //Check to see if email provided matches appropriate pattern
        if (!Tools.verifyEmailFormat(_email)) {
            AlertBox.display(Alert.AlertType.ERROR,
                    "Email address is NOT valid.\n"
                    + "Please verify and try again.",
                    "Email Format Not Compliant", "Email Error");
            return false;
        }

        //Check to see if phone number provided matches appropriate pattern
        if (!_phoneNum.isEmpty() && !Tools.verifyPhoneFormat(_phoneNum)) {
            AlertBox.display(Alert.AlertType.ERROR,
                    "Phone number provided is NOT valid.\n"
                    + "Phone number must be entered in  ###-###-####  format.\n"
                    + "Please verify and try again.",
                    "dddd", "Phone Number Error");
            return false;
        }
        return true;
    }
}