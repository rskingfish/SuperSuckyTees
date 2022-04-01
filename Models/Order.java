package Models;


/**
 * This Class defines an Order object
 * @author rsking, Troy Sanford, Sebastian Schwagerl
 * @version 11/30/18
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import Models.SQL.SQLQueryActions;
import view.AlertBox;


public class Order {

    private static Order currentOrder;

    private int orderID;
    private int userID;
    private Cart cart;
    private double orderPrice;
    private double shippingPrice;
    private int addressID;
    private Date dateCreated;

    private static SQLQueryActions action = new SQLQueryActions();


    //Full Constructor
    public Order(int _userID, Cart _cart, double _orderPrice, double _shippingPrice, int _addressID, Date _dateCreated) {

        this.userID = _userID;
        this.cart = _cart;
        this.orderPrice = _orderPrice;
        this.shippingPrice = _shippingPrice;
        this.addressID = _addressID;
        this.dateCreated = _dateCreated;

        currentOrder = this;
    }


    //Partial Constructor
    public Order(Cart _cart) {

        this.userID = _cart.getUserID();
        this.cart = _cart;
        Date date = new Date();
        this.dateCreated = new java.sql.Date(date.getTime());

        currentOrder = this;
    }


    //Empty Constructor
    public Order() {
    }


    public int getAddress() {
        return addressID;
    }


    public Cart getCart() {
        return cart;
    }


    public static Order getCurrentOrder(){
        return currentOrder;
    }


    public Date getDateCreated() {
        return dateCreated;
    }


    public int getOrderID() {
        return orderID;
    }


    public double getOrderPrice() {
        return orderPrice;
    }


    public double getShippingPrice() {
        return shippingPrice;
    }


    public int getUserID() {
        return userID;
    }

    public static boolean isCreated() {
        return (currentOrder != null);
    }


    public void setAddress(int _addressID) {
        this.addressID = _addressID;
    }


    public void setCart(Cart _cart) {
        this.cart = _cart;
    }


    public static void setCurrentOrder(Order _order){
        currentOrder = _order;
    }


    public void setDateCreated(Date _dateCreated) {
        this.dateCreated = _dateCreated;
    }


    public void setOrderID(int _orderID) {
        this.orderID = _orderID;
    }


    public void setOrderPrice(double _orderPrice) {
        this.orderPrice = _orderPrice;
    }


    public void setShippingPrice(double _shippingPrice) {
        this.shippingPrice = _shippingPrice;
    }


    public void setUserID(int _userID) {
        this.userID = _userID;
    }


    /**
     * Inserts a new entry into Order.
     * @param _userID
     * @param _orderPrice
     * @param _shippingPrice
     * @param _addressID
     * @return
     */
    public static boolean addNewItem(int _userID, double _orderPrice, double _shippingPrice, int _addressID) {

        // Formats today's date.
        Date date = new Date();
        java.sql.Date timestamp = new java.sql.Date(date.getTime());

        // Map column names and values to be used in INSERT statement in SQL.
        Map<String, String> insertValuePairs = new HashMap();
        insertValuePairs.put("userID", String.valueOf(_userID));
        insertValuePairs.put("orderPrice", String.valueOf(_orderPrice));
        insertValuePairs.put("shippingPrice", String.valueOf(_shippingPrice));
        insertValuePairs.put("address", String.valueOf(_addressID));
        insertValuePairs.put("datePlaced", timestamp.toString());

        // Run the SQL statement and return the results.
        return action.insertObject("Order", insertValuePairs);
    }


    /**
     * Sends userID into Select statement to retrieve a list of
     * OrderIDs which are sifted through to find the most recent.
     * @param _userID
     * @return int
     */
    public static int identifyOrderID(int _userID) {

        // Setup for SQL SELECT statement.
        String[] selectTables = {"Order"};
        String[] selectColumns = {"orderID"};
        String[] orderBy = {"orderID"};

        // Set up the where statement by mapping pairs of tables with values.
        Map<String, String> insertValuePairs = new HashMap();
        insertValuePairs.put("userID", Integer.toString(_userID));

        // Store results of SELECT statement in a ResultSet.
        ResultSet results = action.selectObjectBasic(selectTables, selectColumns, insertValuePairs, orderBy);
        int orderID = 0;

        // Take results in the ResultSet and identify the most recent, with the highest orderID.
        // Since orderBy is defaulted ascending, the last value will be the most recent order.
        try {
            while (results.next()) {
                orderID = results.getInt("orderID");
            }
            return orderID;
        }
        catch (SQLException ex) {
            AlertBox.display(ex);
            return 0;
        }
    }
}