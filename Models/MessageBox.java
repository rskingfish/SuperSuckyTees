package Models;


/**
 * Handles the popup windows caused by calling InfoBox.
 * @author Sebastian Schwagerl
 * @version 11/1/18
 */

import javax.swing.*;


public class MessageBox {

    // Formats the pop-up informational windows.
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar,
                JOptionPane.INFORMATION_MESSAGE);
    }
}
