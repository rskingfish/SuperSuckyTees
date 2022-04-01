package Models;


/**
 * Handles the storage and retrieval of a user's address.
 *
 * @author Sebastian Schwagerl
 * @version Nov 25, 2018
 */

import Models.SQL.SQLQueryActions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Address {

    private int addressID;
    private int userID;
    private String addressType;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private String zip;

    private static SQLQueryActions action = new SQLQueryActions();


    /**
     * Full Constructor
     * @param _addressID
     * @param _userID
     * @param _addressType
     * @param _street1
     * @param _street2
     * @param _city
     * @param _state
     * @param _zip
     */
    public Address(int _addressID, int _userID, String _addressType,
            String _street1, String _street2, String _city, String _state, String _zip){

        this.addressID = _addressID;
        this.userID = _userID;
        this.addressType = _addressType;
        this.street1 = _street1;
        this.street2 = _street2;
        this.city = _city;
        this.state = _state;
        this.zip = _zip;
    }


    //Empty Constructor
    public Address() {
    }


    public int getAddressID() {
        return addressID;
    }


    public String getAddressType() {
        return addressType;
    }


    public String getCity() {
        return city;
    }


    public String getState() {
        return state;
    }


    public String getStreet1() {
        return street1;
    }


    public String getStreet2() {
        return street2;
    }


    public int getUserID() {
        return userID;
    }


    public String getZip() {
        return zip;
    }


    public void setAddressID(int _addressID) {
        this.addressID = _addressID;
    }


    public void setAddressType(String _addressType) {
        this.addressType = _addressType;
    }


     public void setCity(String _city) {
        this.city = _city;
    }


    public void setState(String _state) {
        this.state = _state;
    }


    public void setStreet1(String _street1) {
        this.street1 = _street1;
    }


    public void setStreet2(String _street2) {
        this.street2 = _street2;
    }


    public void setUserID(int _userID) {
        this.userID = _userID;
    }


    public void setZip(String _zip) {
        this.zip = _zip;
    }


    /**
     * Checks to see the most recent address used by this user, if any,
     * and then returns it to be auto-filled on the ChangeAddressMenu.
     * @param _userID
     * @return List
     * @throws SQLException
     */
    public static List<Address> checkPreviousAddress(int _userID) throws SQLException {

        String[] selectTables = {"AddressList"};
        String[] selectColumns = {"addressID", "street1", "street2", "city", "state", "zip"};
        String[] orderBy = {"addressID"};

        Map<String, String> insertValuePairs = new HashMap<>();
        insertValuePairs.put("userID", Integer.toString(_userID));
        insertValuePairs.put("addressType", "shipping");
        ResultSet results = action.selectObjectBasic(selectTables, selectColumns, insertValuePairs, orderBy);
        List<Address> addressList = new ArrayList<>();
        while (results.next()) {
            Address address = new Address();
            address.setAddressID(results.getInt("addressID"));
            address.setUserID(results.getInt("userID"));
            address.setStreet1(results.getString("street1"));
            address.setStreet2(results.getString("street2"));
            address.setCity(results.getString("city"));
            address.setState(results.getString("state"));
            address.setZip(results.getString("zip"));
            //user.setAccessType(results.getString("accessType"));
            addressList.add(address);
        }
        return addressList;
    }


    /**
     * Inserts a new address into the database for the specified user.
     * @param _userID
     * @param _addressType
     * @param _street1
     * @param _street2
     * @param _city
     * @param _state
     * @param _zip
     * @return boolean
     * @throws SQLException
     */
    public static boolean saveNewAddress(String _userID, String _addressType,
            String _street1, String _street2, String _city, String _state,
            String _zip) throws SQLException {

        Map<String, String> insertValuePairs = new HashMap<>();
        insertValuePairs.put("userID", _userID);
        insertValuePairs.put("addressType", _addressType);
        insertValuePairs.put("street1", _street1);
        insertValuePairs.put("street2", _street2);
        insertValuePairs.put("city", _city);
        insertValuePairs.put("state", _state);
        insertValuePairs.put("zip", _zip);

        return action.insertObject("AddressList", insertValuePairs);
    }
}
