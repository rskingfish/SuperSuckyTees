package Models;


/**
 * Handles the storage and retrieval of items within the cart.
 * @author Sebastian Schwagerl, rsking
 * @version 12/1/18
 */

import Models.SQL.SQLQueryActions;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import view.AlertBox;


public class Cart {

    private static Cart currentCart;

    private int cartID;
    private int userID;
    private Product product;
    private int amount;
    private Image image;
    private boolean includesImage;
    private String imageFilePath;
    private Date dateCreated;

    private static SQLQueryActions action = new SQLQueryActions();


    //Full Constructor
    public Cart(int _cartID, int _userID, Product _product, int _amount, Image _image, boolean _includesImage) {

        this.cartID = _cartID;
        this.userID = _userID;
        this.product = _product;
        this.amount = _amount;
        this.image = _image;
        this.includesImage = _includesImage;
        this.imageFilePath = null;

        Date date = new Date();
        this.dateCreated = new java.sql.Date(date.getTime());

        currentCart = this;
    }


    //Partial Contstructor
    public Cart(Product _product, int _amount, Image _image) {

        this.userID = User.getCurrentUser().getIDNum();
        this.cartID = 0;
        this.product = _product;
        this.amount = _amount;
        this.image = _image;
        this.includesImage =(_image != null);
        this.imageFilePath = null;

        Date date = new Date();
        dateCreated = new java.sql.Date(date.getTime());

        currentCart = this;
    }


    //Empty Constructor
    public Cart() {
        this.cartID = 0;
        this.userID = 0;
        this.product = null;
        this.amount = 0;
        this.image = null;
        this.includesImage = false;
        this.imageFilePath = null;
        Date date = new Date();
        dateCreated = new java.sql.Date(date.getTime());
    }


    public int getAmount() {
        return amount;
    }


    public int getCartID() {
        return cartID;
    }


    public static Cart getCurrentCart() {
        return currentCart;
    }


    public Date getDateCreated() {
        return dateCreated;
    }


    public Image getImage() {
        return image;
    }


    public String getImageFilePath() {
        return imageFilePath;
    }


    public boolean includesImage() {
        return this.includesImage;
    }


    public static boolean isCreated() {
        return (currentCart == null);
    }


    public Product getProduct() {
        return product;
    }


    public int getUserID() {
        return userID;
    }


    public void setAmount(int _amount) {
        this.amount = _amount;
    }


    public void setCartID(int _cartID) {
        this.cartID = _cartID;
    }


    public static void setCurrentCart(Cart _currentCart) {
        Cart.currentCart = _currentCart;
    }


    public void setDateCreated(Date _dateCreated) {
        this.dateCreated = _dateCreated;
    }


    //Sets image boolean everytime a new image added
    public void setImage(Image _image) {
        this.image = _image;
        if (_image != null) {
            includesImage = true;
        }
    }


    public void setImageFilePath(String _imageFilePath) {
        this.imageFilePath = _imageFilePath;
    }


    public void setIncludesImage(boolean _includesImage) {
        this.includesImage = _includesImage;
    }


    public void setUserID(int _userID) {
        this.userID = _userID;
    }


    public void setProduct(Product _product) {
        this.product = _product;
    }


    /**
     * Inserts a new entry to the cart.
     * @param _userID
     * @param _product
     * @param _amount
     * @param _image
     * @return boolean
     */
    public static boolean addNewItem(int _userID, Product _product, int _amount, Image _image) {

        // Formats today's date.
        Date date = new Date();
        boolean includesImage = false;
        String path = null;

        java.sql.Date timestamp = new java.sql.Date(date.getTime());

        //Determines whether optional image has been included
        if(_image != null) {
            includesImage = true;
            path = Cart.saveToFile(_userID, _image);
        }


        // Map column names and values to be used in INSERT statement in SQL.
        Map<String, String> insertValuePairs = new HashMap<>();
        insertValuePairs.put("userID", String.valueOf(_userID));
        insertValuePairs.put("clothingType", String.valueOf(_product.getClothingType()));
        insertValuePairs.put("size", String.valueOf(_product.getSize()));
        insertValuePairs.put("color", _product.getColor());
        insertValuePairs.put("amount", String.valueOf(_amount));
        insertValuePairs.put("price", String.valueOf(_product.getPrice()));
        insertValuePairs.put("image", String.valueOf(includesImage));
        insertValuePairs.put("dateCreated", timestamp.toString());

        // Run the SQL statement and return the results.
        return action.insertObject("cart", insertValuePairs);
    }


    /**
     * Updates the orderID for all items in the cart assigned to the user that did not have one assigned.
     *
     * @param _userID
     * @param _orderID
     * @return boolean
     */
    public static boolean finalize(int _userID, int _orderID) {
        // Set up the SET statement for Update by mapping columns with values.
        Map<String, String> setValuePairs = new HashMap();
        setValuePairs.put("orderID", String.valueOf(_orderID));

        // Set up the WHERE statement for Update by mapping columns with values.
        Map<String, String> whereValuePairs = new HashMap();
        whereValuePairs.put("userID", String.valueOf(_userID));
        whereValuePairs.put("orderID", String.valueOf(0));

        return action.updateObject("Order", setValuePairs, whereValuePairs);
    }


    /**
     * Clear Cart for User.  To be used when order is created, or manually by the user.
     * @param _userID
     * @return boolean
     */
    public static boolean removeItem(int _userID) {

        // Set up the where statement by mapping pairs of tables with values.
        Map<String, String> removalValuePairs = new HashMap<>();
        removalValuePairs.put("userID", String.valueOf(_userID));

        // Run the SQL statement and return the results.
        return (action.deleteObject("cart", removalValuePairs));
    }


    /**
     * Removes a single selected item from a list of items within a cart.
     * @param _userID
     * @param _cartID
     * @return boolean
     */
    public static boolean removeItem(int _userID, int _cartID) {
        // Set up the where statement by mapping pairs of tables with values.
        Map<String, String> removalValuePairs = new HashMap<>();
        removalValuePairs.put("userID", String.valueOf(_userID));
        removalValuePairs.put("cartID", String.valueOf(_cartID));

        // Run the SQL statement and return the results.
        return (action.deleteObject("cart", removalValuePairs));
    }


    public static void saveImageToDatabase(String _filePath) {

        try
        {
           //convert file to byte array
           FileInputStream fis = new FileInputStream(new File(_filePath));
           ByteArrayOutputStream bos = new ByteArrayOutputStream();
           byte[] buf = new byte[1024];

           try {
                for(int readNum;(readNum = fis.read(buf)) != -1;) {
                    bos.write(buf,0,readNum);
                    System.out.println("read" + readNum + " bytes," );
                }
           } catch (IOException ex) {
            System.out.println(ex);
           }
           byte[] bytes = bos.toByteArray();

           //write to database
           //If it doesn't go well try quoting bytes with ' ', [ ] or " "
           String addRow = "INSERT INTO " + "Image(ID,Picture) VALUES ( " + String.valueOf((int) (Math.random() * 32767)) + "," + bytes;
        } catch(FileNotFoundException ex) {
            AlertBox.display(ex);
        }
    }


    /**
     * Method to store optional image, if included
     * @param _userID
     * @param _image
     * @return
     */
    public static String saveToFile(int _userID, Image _image) {

        FileOutputStream outputFile;

        Random r = new Random();
        int randomInt = r.nextInt(10000) + 1;
        char randomCharUpper = (char) (65 + r.nextInt(25));
        char randomCharLower = (char) (79 + r.nextInt(25));

        try {
            //create unique filename
            String saveTo = "C:\\ImageStorage\\"
                    + String.valueOf(_userID) + "-"
                    + randomCharUpper + randomCharLower + "-"
                    + String.valueOf(randomInt) + ".png";

            outputFile = new FileOutputStream(saveTo);

            BufferedImage bImage = SwingFXUtils.fromFXImage(_image, null);
            ImageIO.write(bImage, "png", outputFile);
            return saveTo;
        } catch (IOException e) {
            AlertBox.display(e);
        }
        return null;
    }


    @Override
    public String toString() {

        return "Product:=" + product.toString() + "\n"
            + "Quantity:=" + String.valueOf(this.amount) + "\n"
            + "Includes Image:=" + includesImage + "\n"
            + "Date Created:=" + dateCreated.toString();
    }


    /**
     * Produces a list of all items within a cart for a user.
     * @param _userID
     * @return
     */
    public static List<Cart> viewCart(int _userID) {

        String[] selectTables = {"Cart"};
        String[] selectColumns = {"cartID", "userID", "clothingType", "size", "color", "amount", "price", "image"};
        String[] orderBy = {};

        Map<String, String> insertValuePairs = new HashMap<>();
        insertValuePairs.put("userID", Integer.toString(_userID));

        ResultSet results;
        List<Cart> cartList = new ArrayList<>();

        try {
            results = action.selectObjectBasic(selectTables, selectColumns, insertValuePairs, orderBy);

            while (results.next()) {
                Cart cart = new Cart();
                Product product = new Product();
                cart.setCartID(results.getInt("cartID"));
                product.setClothingType(results.getInt("clothingType"));
                product.setSize(results.getInt("size"));
                product.setColor(results.getString("color"));
                product.setPrice(results.getDouble("price"));
                cart.setProduct(product);
                cart.setAmount(results.getInt("amount"));
                cart.setIncludesImage(results.getBoolean("image"));
                cartList.add(cart);
            }
            return cartList;
        } catch (SQLException ex) {
            AlertBox.display(ex);
            return null;
        }
    }
}