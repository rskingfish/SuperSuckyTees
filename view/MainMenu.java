package view;


/**
 * MainMenu displays the main menu and serves as the home page for the user.
 * Displays information about product offering and contains a toolbar of
 * buttons at the bottom allowing user to navigate.
 * @author rsking
 * @version 11/25/18
 */

import Models.User;
import Utility.Common;
import Utility.Tools;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainMenu {

    private static Stage mainStage;
    private static Text userText = new Text("");

    /**
     * Builds and display the main menu
     * @param _stage
     */
    public static void displayMainMenu(Stage _stage) {

        mainStage = _stage;

        //Create Company Name Title at Top
        HBox companyBanner = MainMenu.helpBuildCompanyBanner();


        //Create center image as background
        StackPane centerPane = MainMenu.helpBuildBackground();


        //Add text information in upper left area
        HBox information = MainMenu.helpBuildInformation();


        //Add user welcome text visible upon login in upper right area
        HBox userLoggedInAs = MainMenu.helpBuildLoggedInAs(userText);


        //Create Navigational buttons at Bottom
        HBox bottomToolbar = new HBox(Common.STANDARD_HGAP);
        bottomToolbar.setAlignment(Pos.BOTTOM_CENTER);
        bottomToolbar.setPadding(new Insets(10,10,40,10));

        Button shopButton = MainMenu.helpBuildBottomButton("SHOP", Common.MED_BUTTON_WIDTH, "Shop for Clothing", "orange");
        Button loginButton = MainMenu.helpBuildBottomButton("LOG IN", Common.MED_BUTTON_WIDTH, "Account Login", "blue");
        Button createButton = MainMenu.helpBuildBottomButton("CREATE ACCOUNT", Common.LARGE_BUTTON_WIDTH, "Add New User Account", "red");
        Button logoutButton = MainMenu.helpBuildBottomButton("LOG OUT", Common.MED_BUTTON_WIDTH, "Account Logout", "purple");
        Button emailButton = MainMenu.helpBuildBottomButton("EMAIL US", Common.MED_BUTTON_WIDTH, "Send us an email", "green");
        Button exitButton = MainMenu.helpBuildBottomButton("EXIT", Common.MED_BUTTON_WIDTH, "Close/end program", "black");

        bottomToolbar.getChildren().addAll(shopButton, loginButton, createButton, logoutButton, emailButton, exitButton);
        centerPane.getChildren().addAll(userLoggedInAs, information, bottomToolbar);


        //Create a Border Pane and add nodes
        BorderPane layout = new BorderPane();
        layout.setTop(companyBanner);
        layout.setCenter(centerPane);


        //Add scene to stage and show stage
        Scene scene = new Scene(layout, Common.DISPLAY_WIDTH, Common.DISPLAY_HEIGHT);

        mainStage.setTitle("Home");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();


        //************* <<<<<<<<<<<<< ACTIONS >>>>>>>>>>>>> **************
        //On create button click, change control to Create New Account
        createButton.setOnAction(e -> CreateAccountMenu.displayCreateAccount());


        //Change control to Email Controller
        emailButton.setOnAction(e -> Tools.emailUs());


        //On exit button click, close program
        exitButton.setOnAction(e -> MainMenu.terminate());


        //Change control to Login
        loginButton.setOnAction(e -> LoginMenu.displayLoginMenu());


        //On logout button click, log out current user
        logoutButton.setOnAction(e -> {
            User.setCurrentUser(null);
            MainMenu.userText.setText("");
            AlertBox.display("You successfully logged out",
                    "Logout Confirmation");
        });


        //Code for when user closes program via clicking on X vs. exit button
        mainStage.setOnCloseRequest(e -> MainMenu.terminate());


        //On shop button click, change control to Shop Menu
        shopButton.setOnAction(e -> ShopMenu.displayShopping());
    }


    //**** <<<<<<<<<<<<< HELPER METHODS FOR DISPLAY MAIN MENU >>>>>>>>>>>>> ****
    //Build backgound image and add to StackPane
    public static StackPane helpBuildBackground(){
        StackPane centerPane = new StackPane();
        centerPane.setPadding(new Insets(0, 0, 0, 0));
        ImageView background = new ImageView(new Image("images/Background.jpg"));
        background.setFitHeight(754);
        background.setPreserveRatio(true);
        centerPane.getChildren().add(background);

        return centerPane;
    }


    //Build bottom toolbar buttons to specific style
    public static Button helpBuildBottomButton(String _text, int _size,
            String _tooltip, String _color){
        Button button = new Button(_text);
        button.setPrefHeight(50);
        button.setPrefWidth(_size);
        button.setTooltip(new Tooltip(_tooltip));
        button.setEffect(new DropShadow());
        button.setFont(Font.font(null, FontWeight.BOLD, 16));
        button.setStyle("-fx-text-fill: white; "
                + "-fx-background-color: " + _color + ";"
        );

        return button;
    }


    //Build Company Name Title at Top
    public static HBox helpBuildCompanyBanner(){

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        HBox companyBanner = new HBox(Common.STANDARD_HGAP);
        companyBanner.setPadding(new Insets(20,10,20,10));
        companyBanner.setAlignment(Pos.CENTER);
        companyBanner.setStyle("-fx-background-color: khaki");
        Text name = new Text("Super Sucky TEEs");
        name.setEffect(ds);
        name.setCache(true);
        name.setX(10.0f);
        name.setY(270.0f);
        name.setFill(Color.MAROON);
        name.setFont(Font.font("Ravie", FontWeight.BOLD, Common.COMPANY_FONT_SIZE));
        companyBanner.getChildren().add(name);

        return companyBanner;
    }


    //Build information text in center of main menu display
    public static HBox helpBuildInformation(){
        HBox information = new HBox(Common.STANDARD_HGAP);
        information.setAlignment(Pos.TOP_LEFT);
        information.setPadding(new Insets(70,10,10,200));
        Text info = new Text("Welcome to our shirt shop.  We offer a variety of"
                + " shirt styles and colors at competetive prices.  Let us make"
                + " shirts for your next school field trip, company outing, or"
                + " tail-gate party.  Don't forget to take advantage of our"
                + " optional imaging service."
        );
        info.setFont(Font.font(null, FontWeight.MEDIUM, 18));
        info.setFill(Color.WHITE);
        info.setWrappingWidth(500);
        information.getChildren().add(info);

        return information;
    }


    //Build logged in text in upper right corner of main menu display
    public static HBox helpBuildLoggedInAs(Text _userText){
        HBox userLoggedInAs = new HBox(Common.STANDARD_HGAP);
        userLoggedInAs.setAlignment(Pos.TOP_RIGHT);
        userLoggedInAs.setPadding(new Insets(30,200,10,10));
        _userText.setFont(Font.font(null, FontWeight.MEDIUM, 18));
        _userText.setFill(Color.BLUE);
        userLoggedInAs.getChildren().add(_userText);

        return userLoggedInAs;
    }


    //Method to get the X coordinate on the screen of the MainMenu display
    public static double getMainMenuX() {
        return mainStage.getX();
    }


    //Method to get the Y coordinate on the screen of the MainMenu display
    public static double getMainMenuY() {
        return mainStage.getY();
    }


    //Method to run at exit of program
    public static void terminate() {

        //Display warning message and wait for confirmation
        //if(AlertBox.displayExitWarning()) {
        //    System.exit(0);
        //}
        System.exit(0);
    }


    //Method to update the logged in username when changes
    public static void updateUserStatus(){
        if(User.isLoggedIn()){
            userText.setText("Logged in as:  "
                + User.getCurrentUser().getUserName());
            userText.setVisible(true);
        }
    }
}
