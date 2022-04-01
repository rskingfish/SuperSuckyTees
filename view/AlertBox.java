package view;


/**
 * This class displays alert boxes to communicate conditions/information to user
 * @author rsking
 * @version 11/27/18
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;


public class AlertBox {

    /**
     * Formats the pop-up alert messages based on conditions.  This method used
     * for most alerts.
     * @param _alertType:  INFORMATION, ERROR, WARNING, CONFIRMATION
     * @param _message
     * @param _header
     * @param _title
     */
    public static void display(AlertType _alertType, String _message, String _header, String _title) {

        Alert alert = new Alert(_alertType);
                alert.setTitle(_title);
                alert.setHeaderText(_header);
                alert.setContentText(_message);
                alert.showAndWait();
    }


    /**
     * Overridden method reduced to exclude the alert type and header message
     * @param _message
     * @param _title
     */
    public static void display(String _message, String _title) {

        Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle(_title);
                alert.setHeaderText(null);
                alert.setContentText(_message);
                alert.showAndWait();
    }


    /**
     * Overridden method expanded to include lengthy Error message details in display
     * @param _ex
     */
    public static void display(Exception _ex) {

        Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Exception Error");
                alert.setHeaderText("Error, Failed Operation");
                alert.setContentText("Could not complete transaction");

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        _ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Exception details:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane content = new GridPane();
        content.setMaxWidth(Double.MAX_VALUE);
        content.add(label, 0, 0);
        content.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(content);
        alert.showAndWait();
    }


    /**
     * Method prompts user to confirm exit while items left in cart
     * @return
     */
    public static boolean displayExitWarning() {

        Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Exit Confirmation");
                alert.setHeaderText("There are still items in your Cart.\n"
                        + "These will be lost on exit.");
                alert.setContentText("Do you still want to exit?");

        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }
}
