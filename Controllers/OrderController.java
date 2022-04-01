package Controllers;


/**
 * Handles the pathing between the CartMenu to the Order Model.
 * @author Sebastian Schwagerl
 * @version Nov 30, 2018
 */

public class OrderController {


    /**
     * Passes the userID and final order results to be added to the Order table..
     * @param _userID
     * @param _orderPrice
     * @param _shippingPrice
     * @param _addressID
     * @return boolean
     */
    public static boolean finalizeOrder(int _userID, double _orderPrice,
            double _shippingPrice, int _addressID) {

        return Models.Order.addNewItem(_userID, _orderPrice, _shippingPrice, _addressID);
    }


    /**
     * Passes the userID to retrieve their most recent OrderID.
     * @param _userID
     * @return int
     */
    public static int identifyOrderID(int _userID) {

        return Models.Order.identifyOrderID(_userID);
    }
}
