package view;


/**
 *
 * @author Sebastian Schwagerl
 * @version 12/1/18
 */

import Controllers.AddressController;
import Models.Address;
import Models.MessageBox;
import Models.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.text.ParseException;


public class ChangeAddressMenu {


    public static void displaySetAddress(User _user) throws SQLException, ParseException {

        // Setup New Buttons.
        Button btnSetAddress = new Button("Confirm");
        Button btnBack = new Button("Back");
        btnBack.setCancelButton(true);

        // Setup new Stage.
        Stage addressStage = new Stage();
        addressStage.setTitle("Create New Account");
        addressStage.initModality(Modality.APPLICATION_MODAL);

        // Setup new GridPane.
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 170, 10, 10));
        grid.setVgap(8);
        grid.setHgap(12);

        // Setup new Labels.
        Label lblStreet1 = new Label("Street Address: ");
        Label lblStreet2 = new Label("Street Address: ");
        Label lblCity = new Label("City: ");
        Label lblState = new Label("State: ");
        Label lblZip = new Label("Zip Code: ");

        // Setup new Textboxes.
        TextField txtStreet1 = new TextField();
        txtStreet1.setPromptText("Enter Address");
        TextField txtStreet2 = new TextField();
        TextField txtCity = new TextField();
        txtCity.setPromptText("Enter City");
        TextField txtState = new TextField();
        txtState.setPromptText("Enter State");
        TextField txtZip = new TextField();
        txtZip.setPromptText("Enter Zip");

        // Change the GridPane Column Lengths.
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(75);
        col1.setMaxWidth(75);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(10);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(10);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(30);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setMinWidth(15);
        col5.setMaxWidth(15);
        ColumnConstraints col6 = new ColumnConstraints();
        col6.setPercentWidth(30);
        ColumnConstraints col7 = new ColumnConstraints();
        col7.setPercentWidth(10);
        ColumnConstraints col8 = new ColumnConstraints();
        col8.setPercentWidth(10);
        ColumnConstraints col9 = new ColumnConstraints();
        col9.setMinWidth(50);
        col9.setMaxWidth(50);
        ColumnConstraints col10 = new ColumnConstraints();
        col10.setMinWidth(20);
        col10.setMaxWidth(20);

        // Assign Column properties to Grid Pane.
        grid.getColumnConstraints().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);

        // Position objects onto Grid Pane.
        grid.setConstraints(lblStreet1, 0, 0, 2, 1);
        grid.setConstraints(txtStreet1, 2, 0, 7, 1);
        grid.setConstraints(lblStreet2, 0, 1, 2, 1);
        grid.setConstraints(txtStreet2, 2, 1, 7, 1);
        grid.setConstraints(lblCity, 0, 2, 2, 1);
        grid.setConstraints(txtCity, 2, 2, 7, 1);
        grid.setConstraints(lblState, 0, 3, 2, 1);
        grid.setConstraints(txtState, 2, 3, 7, 1);
        grid.setConstraints(lblZip, 0, 4, 2, 1);
        grid.setConstraints(txtZip, 2, 4, 7, 1);
        //grid.setConstraints(lblPhoneNum, 0, 5, 2, 1);
        //grid.setConstraints(txtPhoneNum, 2, 5, 7, 1);
        grid.setConstraints(btnSetAddress, 3, 7, 1, 1);
        grid.setConstraints(btnBack, 5, 7, 1, 1);

        // Fill objects into their container.
        grid.setFillWidth(lblStreet1, true);
        grid.setFillHeight(lblStreet1, true);
        grid.setHalignment(lblStreet1, HPos.RIGHT);
        grid.setFillWidth(txtStreet1, true);
        grid.setFillHeight(txtStreet1, true);
        grid.setFillWidth(lblStreet2, true);
        grid.setFillHeight(lblStreet2, true);
        grid.setHalignment(lblStreet2, HPos.RIGHT);
        grid.setFillWidth(txtStreet2, true);
        grid.setFillHeight(txtStreet2, true);
        grid.setFillWidth(lblCity, true);
        grid.setFillHeight(lblCity, true);
        grid.setHalignment(lblCity, HPos.RIGHT);
        grid.setFillWidth(txtCity, true);
        grid.setFillHeight(txtCity, true);
        grid.setFillHeight(lblState, true);
        grid.setHalignment(lblState, HPos.RIGHT);
        grid.setFillWidth(txtState, true);
        grid.setFillHeight(txtState, true);
        grid.setFillHeight(lblZip, true);
        grid.setHalignment(lblZip, HPos.RIGHT);
        grid.setFillWidth(txtZip, true);
        grid.setFillHeight(txtZip, true);
        //grid.setFillHeight(lblPhoneNum, true);
        //grid.setHalignment(lblPhoneNum, HPos.RIGHT);
        //grid.setFillWidth(txtPhoneNum, true);
        //grid.setFillHeight(txtPhoneNum, true);
        grid.setFillWidth(btnSetAddress, true);
        grid.setFillWidth(btnBack, true);

        // Add Objects to the scene and show.
        grid.getChildren().addAll(lblStreet1, txtStreet1,
                lblStreet2, txtStreet2,
                lblCity, txtCity,
                lblState, txtState,
                lblZip, txtZip,
                btnSetAddress, btnBack);
        Scene scene = new Scene(grid, 490, 260);
        addressStage.setScene(scene);
        addressStage.setResizable(true);
        addressStage.setMinWidth(450);
        addressStage.setMinHeight(200);
        addressStage.show();

        // Attempt to see if user has made a previous order, and find most recentAddress used.
        Address previousAddress = AddressController.checkPreviousAddress(_user.getIDNum());
        if (previousAddress != null) {
            txtStreet1.setText(previousAddress.getStreet1());
            txtStreet2.setText(previousAddress.getStreet2());
            txtCity.setText(previousAddress.getCity());
            txtState.setText(previousAddress.getState());
            txtZip.setText(previousAddress.getZip());
        }

        // If btnBack is clicked, the page closes.
        btnBack.setOnAction(e -> addressStage.close());

        // If btnRegister is clicked, the Registration attempt is sent to the UserRegistration Class.
        btnSetAddress.setOnAction(e -> {
            try {
                if (validate(Integer.toString(_user.getIDNum()), "Shipping", txtStreet1.getText(), txtStreet2.getText(), txtCity.getText(), txtState.getText(), txtZip.getText()) == true) {
                    //LoginMenu.displayLogIn();
                    //addressStage.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }


    public static boolean validate(String _userID, String _addressType, String _street1, String _street2, String _city, String _state, String _zipCode) throws SQLException {

        // Confirm that all textboxes have an entry within them.
        if (_street1.length() == 0 || _street2.length() == 0 || _city.length() == 0 || _state.length() == 0 || _zipCode.length() == 0) {
            MessageBox.infoBox("Please fill in all of the boxes before continuing.", "Insufficient Entry");
            return false;
        }

        return AddressController.registerNewAddress(_userID, _addressType, _street1, _street2, _city, _state, _zipCode);
    }
}
