package view;


/**
 * CreateAccountMenu displays the registration form to the user and manages
 * manages the creation of all new accounts.
 * @author Sebastian Schwagerl, rsking
 * @version 11/25/18
 */

import Controllers.UserController;
import Models.User;
import Utility.Common;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;


public class CreateAccountMenu {


    public static void displayCreateAccount() {

        //Setup New Buttons
        Button btnRegister = new Button("Register");
        Button btnBack = new Button("Cancel");
        btnBack.setCancelButton(true);

        //Setup new Stage
        Stage registrationStage = new Stage();
        registrationStage.setTitle("Create New Account");
        registrationStage.initModality(Modality.APPLICATION_MODAL);

        //Setup new GridPane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(Common.STANDARD_VGAP);
        grid.setHgap(Common.STANDARD_HGAP);

        //Setup new Labels and TextBoxes
        Label lblUsername = new Label("Username: ");
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Enter Username");

        Label lblPassword = new Label("Password: ");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Enter Password");

        Label lblRepeatPassword = new Label("Re-Password: ");
        PasswordField txtRepeatPassword = new PasswordField();
        txtRepeatPassword.setPromptText("Re-Enter Password");

        Label lblFullName = new Label("First & Last Name: ");
        TextField txtFullName = new TextField();
        txtFullName.setPromptText("First then Last");

        Label lblEmail = new Label("Email: ");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Enter Email Address");

        Label lblPhoneNum = new Label("Phone #: ");
        TextField txtPhoneNum = new TextField();
        txtPhoneNum.setPromptText("555-555-5555");


        //Change the GridPane Column Lengths
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(120);
        col1.setHalignment(HPos.RIGHT);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMinWidth(330);
        grid.getColumnConstraints().addAll(col1, col2);

        //Add Objects to the scene and show.
        grid.addRow(0, lblUsername, txtUsername);
        grid.addRow(1, lblPassword, txtPassword);
        grid.addRow(2, lblRepeatPassword, txtRepeatPassword);
        grid.addRow(3, lblFullName, txtFullName);
        grid.addRow(4, lblEmail, txtEmail);
        grid.addRow(5, lblPhoneNum, txtPhoneNum);
        grid.addRow(6, btnRegister, btnBack);

        Scene scene = new Scene(grid, 500, 280);
        registrationStage.setScene(scene);
        registrationStage.setResizable(true);
        registrationStage.setMinWidth(500);
        registrationStage.setMinHeight(280);
        registrationStage.show();


        //************* <<<<<<<<<<<<< ACTIONS >>>>>>>>>>>>> **************
        //If btnBack is clicked, the page closes.
        btnBack.setOnAction(e -> {
            registrationStage.close();
        });


        //On btnRegister click, attempt registration via UserController Class.
        btnRegister.setOnAction(e -> {

            if(UserController.registration(txtUsername.getText(), txtPassword.getText(),
                    txtRepeatPassword.getText(), txtFullName.getText(),
                    txtEmail.getText(), txtPhoneNum.getText())) {

                AlertBox.display(Alert.AlertType.INFORMATION,
                    "Welcome: " + txtUsername.getText() + "\n"
                    + "Your new account has been successfully created.\n"
                    + "You are ready to shop.",
                    "New Account Confirmation", "Account Successfully Created");

                //login new user immediately after registration
                User user = UserController.loginUser(txtUsername.getText(), txtPassword.getText());
                user.setCurrentUser(user);
                MainMenu.updateUserStatus();
            }

            if(User.isLoggedIn()){
                registrationStage.close();
            }
        });
    }
}