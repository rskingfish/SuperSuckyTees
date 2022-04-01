package Controllers;


/**
 * Handles the pathing between the ChangeAddressMenu to the Address Model.
 * @author Sebastian Schwagerl
 * @version Nov 25, 2018
 */

import Models.Address;
import Models.MessageBox;
import java.sql.SQLException;
import java.util.List;


public class AddressController {


    /**
     * Checks for the most recently used address by the user and
     * sends back the Address to fill in the Textboxes automatically.
     * @param _userID
     * @return Address
     * @throws SQLException
     */
    public static Address checkPreviousAddress(int _userID) throws SQLException {

        List<Address> addressList;
        addressList = Models.Address.checkPreviousAddress(_userID);
        Address addressInfo = null;
        if (addressList.size() > 0){
            addressInfo = addressList.get(0);
        }

        return addressInfo;
    }


    /**
     * Adds a new Address to the user, which will be used when finalizing an
     * order and determining shipping costs.
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
    public static boolean registerNewAddress(String _userID, String _addressType,
                String _street1, String _street2, String _city, String _state,
                String _zip) throws SQLException {

        if (Models.Address.saveNewAddress(_userID, _addressType, _street1,
                _street2, _city, _state, _zip) == true) {

            MessageBox.infoBox("The address was successfully inserted into the "
                    + "order.", "Address Successfully Inserted");
            return true;
        }
        MessageBox.infoBox("An error has occurred while trying to add the "
                + "Address.\nPlease try again.", "Error");
        return false;
    }
}