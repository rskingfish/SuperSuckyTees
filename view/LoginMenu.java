package view;


/**
 * LoginMenu displays the login menu, retrieves username/password and passes
 * to the UserController for verification.  It also provides a hyperlink to
 * the CreateAccountMenu.
 * @author rsking
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class LoginMenu {


    public static void displayLoginMenu() {

        Stage loginStage = new Stage();
        loginStage.setTitle("Please Sign In");
        loginStage.initModality(Modality.APPLICATION_MODAL);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(60, 20, 20, 60));
        layout.setVgap(Common.STANDARD_VGAP);

        //Add hyperlink for new account creation
        Hyperlink createAccount = new Hyperlink("Create New Account");
        TextFlow flow = new TextFlow(createAccount);

        Button loginButton = new Button("Login");
        loginButton.setPrefSize(120, 40);

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefSize(90, 40);

        //Add error message, but initialy set not visible
        Text errorMessage = new Text("Username/Password is incorrect");
        errorMessage.setFill(Color.CRIMSON);
        errorMessage.setVisible(false);

        TextField userName = new TextField();
        userName.setMinWidth(170);
        userName.setPromptText("Enter Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Enter Password");

        layout.addRow(2, new Label("Enter Username"), userName);
        layout.addRow(3, new Label("Enter Password"), password);
        layout.add(errorMessage, 0, 5, 2, 1);
        layout.addRow(6, loginButton, cancelButton);
        layout.add(flow, 1, 0);

        GridPane.setHalignment(cancelButton, HPos.CENTER);
        GridPane.setHalignment(errorMessage, HPos.CENTER);

        Scene scene = new Scene(layout, 400, 250);
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.show();


        //************* <<<<<<<<<<<<< ACTIONS >>>>>>>>>>>>> **************
        //close login controller without updating logged user
        cancelButton.setOnAction(e -> loginStage.close());


        //redirect to LoginController to create new account
        createAccount.setOnAction(e -> {
            CreateAccountMenu.displayCreateAccount();
            loginStage.close();
        });


        //verify correct user/password entry and update logged user
        loginButton.setOnAction(e -> {

            //Check to see if the username or password are empty.
            if(userName.getText().isEmpty() || password.getText().isEmpty()) {
                AlertBox.display(Alert.AlertType.ERROR,
                        "Please enter username and password",
                        "Username or password information missing",
                        "Login Information Missing");
                return;
            }

            //Check to see if username and password are valid.
            User user = UserController.loginUser(userName.getText(), password.getText());
            if(user != null) {
                AlertBox.display(Alert.AlertType.INFORMATION,
                        "Logged in as: " + userName.getText(),
                        "Successful Login Completed",
                        "Confirmed Login");
                user.setCurrentUser(user);
                MainMenu.updateUserStatus();
                loginStage.close();
            }

            //If login unsuccessful, display error message and clear fields
            else {
                AlertBox.display(Alert.AlertType.ERROR,
                        "The username or password entered is not correct.\n"
                        + "Please try again.",
                        "Incorrect Information Provided",
                        "Username and Password mismatch");
                userName.clear();
                password.clear();
                errorMessage.setVisible(true);
            }
        });
    }
}
