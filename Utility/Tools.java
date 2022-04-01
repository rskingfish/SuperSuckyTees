package Utility;


/**
 * Various tools for shipping calculations, edit checks, and email communication
 * @author rsking
 * @version 11/25/18
 */

import Models.User;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import view.AlertBox;


public class Tools {


    /**
     * Calculate weight of specific clothing item
     * BASE represents weight of size SMALL for each item
     * UPSIZE represents incremental item weight for each change in size
     * @param _clothingType: integer value correlating to clothing type
     * @param _clothingSize: integer value correcting to clothing size
     * @return : shipping weight for a specific item
     */
    public static double calculateItemWeight(int _clothingType, int _clothingSize) {

        switch (_clothingType) {

            case ClothingUtility.TANK_TOP:
                return ClothingUtility.TANK_TOP_BASE + ((_clothingSize - 1) *
                        ClothingUtility.TANK_TOP_UPSIZE);
            case ClothingUtility.SHORT_SHIRT:
                return ClothingUtility.SHORT_SHIRT_BASE + ((_clothingSize - 1) *
                        ClothingUtility.SHORT_SHIRT_UPSIZE);
            case ClothingUtility.LONG_SHIRT:
                return ClothingUtility.LONG_SHIRT_BASE + ((_clothingSize - 1) *
                        ClothingUtility.LONG_SHIRT_UPSIZE);
            case ClothingUtility.SWEATSHIRT:
                return ClothingUtility.SWEATSHIRT_BASE + ((_clothingSize - 1) *
                        ClothingUtility.SWEATSHIRT_UPSIZE);
            case ClothingUtility.HOODIE:
                return ClothingUtility.HOODIE_BASE + ((_clothingSize - 1) *
                        ClothingUtility.HOODIE_UPSIZE);
            default:
                return 0.0;
        }
    }


    /**
     * Calculate the shipping weight for a quantity of clothing Items
     * @param _clothingType: integer value correlating to clothing type
     * @param _clothingSize: integer value correcting to clothing size
     * @param _quantity: count of clothing items
     * @return: total shipping weight of items
     */
    public static double calculateWeight(int _clothingType, int _clothingSize,
            int _quantity) {
        return _quantity * calculateItemWeight(_clothingType, _clothingSize);
    }


    /**
     * Method to send email confirmation of order details at completion of sale
     * @param _orderDetails
    */
    public static void emailOrderConfirmation(String _orderDetails) {

        final String to = User.getCurrentUser().getEmail();
        final String subject = "Order Confirmation";
        final String from = "orders.SuperSuckyTees@gmail.com";
        final String username = "orders.SuperSuckyTees@gmail.com";
        final String password = "csc340-SST";
        final String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        //Get the Session object.
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            //Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            //Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            //Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            //Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(_orderDetails);

            // Send message
            Transport.send(message);

            System.out.println("Order confirmation email sent");

        } catch (MessagingException e) {
            AlertBox.display(e);
        }
    }


    /**
     * Method to initiate user's external desktop email application so that user
     * can send email to us.
     */
    public static void emailUs() {
        Desktop desktop;
        if (Desktop.isDesktopSupported()
                && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            //URI mailto = null;
            try {
                URI mailto = new URI("mailto:contactUs.SuperSuckyTees@gmail.com?subject=Contact%20Us");
                desktop.mail(mailto);
            } catch (URISyntaxException | IOException ex) {
                AlertBox.display(ex);
            }
        }
        else {
            AlertBox.display("Desktop doesn't support mailto", "Runtime Exception");
        }
    }


    /**
     * Method to verify that email address provided follows acceptable pattern
     * @param _email
     * @return
     */
    public static boolean verifyEmailFormat(String _email){

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher((CharSequence) _email);
        return matcher.matches();
    }


    /**
     * Method to verify that phone number provided follows acceptable pattern
     * @param _phone
     * @return
     */
    public static boolean verifyPhoneFormat(String _phone){

        String regex = "\\d{3}-\\d{3}-\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(_phone);

        return matcher.matches();
    }
}
