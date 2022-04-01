package Controllers;


/**
 * Class handles the interaction with the shipping menu and the shipping API
 * @author rsking
 * @version 12/2/18
 */

import view.ShippingMenu;


public class ShippingController {


    //Method to return the rush shipping
    public static double getRushShipping() {

        return ShippingMenu.getRushOption();
    }


    //Method to reset rush shipping back 0 after clearing cart
    public static void resetRushShipping() {

        ShippingMenu.resetRushOption();
    }


    //Method to call Shipping Menu until shipping API completed
    public static double updateShippingCosts() {

        //Stub out waiting for Troy's code
        ShippingMenu.displayShippingMenu();

        return 0.0;
    }
}
