package Controllers;


/**
 * Handles the pathing between the ShopMenu and CartMenu to the Cart Model.
 * @author Sebastian Schwagerl
 * @version Nov 25, 2018
 */

import Models.Cart;
import java.util.List;
import Models.Product;
import javafx.scene.image.Image;


public class CartController {


    /**
     * Triggers when add to cart is pressed in the Shop Menu view.
     * It checks for possible errors before passing on the information for
     * query creation to insert an order into the Cart table.
     * @param _userID
     * @param _product
     * @param _amount
     * @param _image
     * @return boolean
     */
    public static boolean addToCart(int _userID, Product _product, int _amount, Image _image) {

        // Attempt to add an entry to the cart.
        return Models.Cart.addNewItem(_userID, _product, _amount, _image);
    }


    /**
     * Passes the userID to have all cart items for that user Removed.
     * @param _userID
     * @return boolean
     */
    public static boolean clearCart(int _userID) {

        // Removes all items within the cart, tied to the given userID.
        return Models.Cart.removeItem(_userID);
    }


    /**
     * Passes on the userID and orderID to assign all unassigned cart items to
     * the recently created order.
     * @param _userID
     * @param _orderID
     * @return boolean
     */
    public static boolean finalize(int _userID, int _orderID) {

        return Models.Cart.finalize(_userID, _orderID);
    }


    /**
     *
     * @param _userID
     * @return boolean
     */
    public static boolean finalizeOrder(int _userID) {

        // May need more inputs but in general, all items in the cart tied to
        // the user is moved into the Order table, along with the price.
        // Afterwards, it will delete every entry that contains said userID from the Cart table.
        return true;
    }


    /**
     * Passes the user and cartID to remove a specific item from the cart.
     * @param _userID
     * @param _cartID
     * @return boolean
     */
    public static boolean removeFromCart(int _userID, int _cartID) {

        // Removes an individual entry from the cart.
        // When added, and displayed on the ShopMenu,
        //it should carry its cartID with it.
        return Models.Cart.removeItem(_userID, _cartID);
    }


    /**
     * Passes userID to retrieve all cart values in a list; displayable on the
     * table in CartMenu.
     * @param _userID
     * @return
     */
    public static List<Cart> viewCart(int _userID) {

        // Retrieves all cart entries for userID.
        List<Cart> cartList;
        cartList = Models.Cart.viewCart(_userID);
        return cartList;
    }
}
