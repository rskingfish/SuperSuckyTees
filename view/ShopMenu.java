package view;


/**
 * ShopMenu displays the shopping menu and serves as the view for the user to
 * custom select from style, size, and color options.  User may add an
 * optional image to the clothing article.  After selections made, clicking
 * add to cart button will pass selection info to the cart.
 * @author rsking
 * @version 12/1/18
 */

import Controllers.CartController;
import Models.Cart;
import Models.Order;
import Utility.ClothingUtility;
import Utility.Common;
import Models.Product;
import Models.User;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import static javafx.scene.control.ContentDisplay.TOP;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ShopMenu {

    //Local variables to track user selections
    private static int clothingChosen = 0;
    private static int sizeChosen = 0;
    private static String colorChosen = null;
    private static int quantityChosen = 0;
    private static double priceChosen = 0.0;
    private static ImageView logo;
    private static Image imageChosen = null;
    private static Text unitPrice = new Text("0.00");
    private static Text totalPrice = new Text("$0.00");

    private static SVGPath path = ShopMenu.buildPath();
    private static SVGPath back = ShopMenu.buildPath();


    /**
     * Helper method to choose the file location for the clothing image STEP 5
     * @return: file path for image
     * @throws Exception
     */
    public static String addImage() throws Exception {

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.gif", "*.png"));
        File imageFile = chooser.showOpenDialog(null);

        return imageFile.toString();
    }


    public static void applyColor(Paint _color){

        path.setFill(_color);
        back.setFill(_color);

        ShopMenu.colorChosen = _color.toString();
    }


    /**
     * Helper method to set up SVGPath formatting
     * @return
     */
    public static SVGPath buildPath(){

        SVGPath path = new SVGPath();
        path.setStrokeWidth(2);
        path.setStroke(Color.BLACK);
        path.setFill(Color.WHITE);
        path.setEffect(new DropShadow());
        path.setVisible(true);

        return path;
    }


    public static void displayShopping() {


        //********** BUILD LEFT SIDE OF SHOPPING DISPLAY **********
        //Create instruction step bars for functional area
        Group step1 = ShopMenu.helpBuildInstructionSteps("1", "Select Product Category");
        Group step2 = ShopMenu.helpBuildInstructionSteps("2", "Select Size");
        Group step3 = ShopMenu.helpBuildInstructionSteps("3", "Choose Color");
        Group step4 = ShopMenu.helpBuildInstructionSteps("4", "Enter Quantity");
        Group step5 = ShopMenu.helpBuildInstructionSteps("5", "Add Image (optional)");


        //Add buttons for shirt categories selection (STEP 1)
        Button tankTopButton = ShopMenu.helpBuildClothingButton("Tank Tops", "images/TankTopThumb.png");
        Button tShirtButton = ShopMenu.helpBuildClothingButton("T-Shirts", "images/ShortSleeveThumb.png");
        Button longShirtButton = ShopMenu.helpBuildClothingButton("Long-Sleeve Shirts", "images/LongSleeveThumb.png");
        Button sweatShirtButton = ShopMenu.helpBuildClothingButton("Sweat Shirts", "images/SweatShirtThumb.png");
        Button hoodieButton = ShopMenu.helpBuildClothingButton("Hoodies", "images/HoodieThumb.png");


        //Add prices for each shirt type (STEP 1)
        Text tankPrice = ShopMenu.helpBuildPrices(ClothingUtility.TANK_TOP_PRICE);
        Text shortPrice = ShopMenu.helpBuildPrices(ClothingUtility.SHORT_SHIRT_PRICE);
        Text longPrice = ShopMenu.helpBuildPrices(ClothingUtility.LONG_SHIRT_PRICE);
        Text sweatPrice = ShopMenu.helpBuildPrices(ClothingUtility.SWEATSHIRT_PRICE);
        Text hoodiePrice = ShopMenu.helpBuildPrices(ClothingUtility.HOODIE_PRICE);


        //Add buttons and prices to gridpane (STEP 1)
        GridPane gridClothing = ShopMenu.helpBuildGridClothing();
        gridClothing.addRow(0, tankTopButton, tShirtButton,longShirtButton, sweatShirtButton, hoodieButton);
        gridClothing.addRow(1, tankPrice, shortPrice, longPrice, sweatPrice, hoodiePrice);


        //Size Options for Selection (STEP 2)
        ComboBox<String> sizeComboBox = ShopMenu.helpBuildSizeComboBox();

        HBox hBoxSize = new HBox(Common.STANDARD_HGAP);
        hBoxSize.setPadding(Common.STANDARD_INSETS);
        hBoxSize.getChildren().add(sizeComboBox);


        //Color Options for Selection (STEP 3)
        Circle[] colorOption = new Circle[ClothingUtility.AVAILABLE_COLORS.length];
        GridPane gridColors = helpBuildGridColors(colorOption);


        //Box for user to provide quantity (STEP 4)
        Text textX = new Text("X $");
        Text textEach = new Text("each =");
        totalPrice.setFill(Color.NAVY);
        totalPrice.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));

        TextField quantityBox = new TextField();
        quantityBox.setPrefWidth(170);
        quantityBox.setPromptText("Enter quantity here");


        HBox hBoxQuantity = new HBox(Common.STANDARD_HGAP);
        hBoxQuantity.setPadding(Common.STANDARD_INSETS);
        hBoxQuantity.setAlignment(Pos.CENTER_LEFT);
        hBoxQuantity.getChildren().addAll(quantityBox, textX, unitPrice,
                textEach, totalPrice);


        //Optional select image to add to clothing (STEP 5)
        Button imageButton = new Button("Add Image");
        imageButton.setPrefWidth(Common.LARGE_BUTTON_WIDTH);

        //Button to clear out/remove any selected image
        Button removeButton = new Button("Remove Image");
        removeButton.setPrefWidth(Common.LARGE_BUTTON_WIDTH);

        //Filepath for the image file selected
        Text filePath = new Text();
        filePath.setFill(Color.CRIMSON);
        filePath.setWrappingWidth(500);
        filePath.setVisible(false);

        GridPane gridImage = new GridPane();
        gridImage.setPadding(Common.STANDARD_INSETS);
        gridImage.setHgap(50);
        gridImage.setVgap(Common.STANDARD_VGAP);
        gridImage.addRow(0, imageButton, removeButton);
        gridImage.add(filePath, 0, 1, 2, 1);


        //Add all items on left side
        VBox leftSide = new VBox(Common.STANDARD_VGAP);
        leftSide.getChildren().addAll(step1, gridClothing, step2, hBoxSize,
                step3, gridColors, step4, hBoxQuantity, step5, gridImage);


        //********** BUILD RIGHT SIDE OF SHOPPING DISPLAY **********
        //Toolbar navigational buttons
        ImageView imageView = new ImageView(new Image("images/HomeButtonIcon.png"));
        Button homeButton = new Button("Home", imageView);
        homeButton.setPrefWidth(Common.MED_BUTTON_WIDTH);
        homeButton.setTooltip(new Tooltip("Go to Home Page"));

        imageView = new ImageView(new Image("images/AccountButtonIcon.png"));
        Button loginButton = new Button("My Account", imageView);
        loginButton.setPrefWidth(Common.MED_BUTTON_WIDTH);
        loginButton.setTooltip(new Tooltip("Log in to Account"));

        imageView = new ImageView(new Image("images/CartButtonIcon.png"));
        Button cartButton = new Button("View Cart", imageView);
        cartButton.setPrefWidth(Common.MED_BUTTON_WIDTH);
        cartButton.setTooltip(new Tooltip("Show Cart"));

        HBox hBoxMenu = new HBox(Common.STANDARD_HGAP);
        hBoxMenu.setPadding(new Insets(20,0,10,10));
        hBoxMenu.getChildren().addAll(
                homeButton, loginButton, cartButton);


        //Prototype shirt to display in rectangle with optional image logo
        Rectangle shirtBorder = helpBuildShirtBorder();

        logo = new ImageView(new Image("images/java8.png"));
        logo.setVisible(false);

        Group shirtRectangle = new Group();
        shirtRectangle.getChildren().addAll(shirtBorder, path, back, logo);
        HBox hBoxShirt = new HBox(Common.STANDARD_HGAP);
        hBoxShirt.getChildren().addAll(shirtRectangle);
        hBoxShirt.setAlignment(Pos.CENTER);


        //Add to cart button
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setPrefSize(200, 40);
        Button resetButton = new Button("Clear All Selections");
        resetButton.setPrefSize(200, 40);

        HBox hBoxAddCart = new HBox(Common.STANDARD_HGAP);
        hBoxAddCart.setPadding(new Insets(10,0,10,0));
        hBoxAddCart.setAlignment(Pos.CENTER);
        hBoxAddCart.getChildren().addAll(addToCartButton, resetButton);


        //Add all items on the right side
        VBox rightSide = new VBox(Common.STANDARD_VGAP);
        rightSide.getChildren().addAll(hBoxMenu, hBoxShirt, hBoxAddCart);


        //Add left and right sides to shop gridpane
        GridPane shopPane = new GridPane();
        shopPane.setPadding(new Insets(25,50,50,50));
        shopPane.setHgap(50);
        shopPane.addRow(0, leftSide, rightSide);


        //Show shopping stage
        Scene scene = new Scene(shopPane, Common.DISPLAY_WIDTH, Common.DISPLAY_HEIGHT);
        Stage shopStage = new Stage();
        shopStage.initModality(Modality.APPLICATION_MODAL);
        shopStage.setTitle("Shopping");
        shopStage.setScene(scene);
        shopStage.setResizable(false);
        shopStage.setX(MainMenu.getMainMenuX());
        shopStage.setY(MainMenu.getMainMenuY());
        shopStage.show();


        //*************** <<<<<<<<<<<<< ACTIONS >>>>>>>>>>>>> ****************
        //On add to cart button click, add item to cart
        addToCartButton.setOnAction(e -> {

            //Verify selection of all required choices
            if (clothingChosen == 0 || sizeChosen == 0 || colorChosen == null || quantityChosen == 0) {
                AlertBox.display(Alert.AlertType.ERROR,
                        "One or more required selections is missing.\n"
                        + "You must choose:  clothing type, size, color, and quantity",
                        "Additional Informatin Required", "Missing Information");
                return;
            }

            //Check to see if logged in
            if(!User.isLoggedIn()){
                AlertBox.display(Alert.AlertType.INFORMATION,
                        "Not logged in...\n"
                        + "You must be logged in to add items to your cart.\n"
                        + "You can log in by clicking the 'My Account' button "
                        + "in the upper right.",
                        "Login Required to Proceed", "Missing Login");
                return;
            }

            //Create an instance of the product selected
            Product product = new Product(clothingChosen, sizeChosen, colorChosen, priceChosen);
            Product.setCurrentProduct(product);

            //Add product to the cart
            Cart cart = new Cart(product, quantityChosen, imageChosen);
            Cart.setCurrentCart(cart);

            //Add cart to the order
            if(!Order.isCreated()) {
                Order order = new Order(Cart.getCurrentCart());
                Order.setCurrentOrder(order);
            }

            //Add to database
            CartController.addToCart(User.getCurrentUser().getIDNum(), product, quantityChosen, imageChosen);

            //Display confirmation message that item added to cart
            AlertBox.display(Alert.AlertType.INFORMATION,
                    "Item chosen has been added to your cart.\n"
                    + "Click the cart button to view cart contents or continue "
                    + "shopping",
                    "Your cart has a new item", "Item Added to Cart Confirmation");

            //Reset shopping menu form
            ShopMenu.resetShopMenu(sizeComboBox, quantityBox, filePath);
        });


        //On cart button click, display and change control to Cart
        cartButton.setOnAction(e -> {
            if(!User.isLoggedIn()) {
                AlertBox.display("You must be logged in to view cart contents\n"
                    + "You can login by clicking the 'My Account' button.",
                    "Login Missing");
                return;
            }
            CartMenu.displayCart();
            shopStage.close();
        });


        //On home button click, close shopping and return to home page
        homeButton.setOnAction(e -> {
            shopStage.close();
        });


        //On clothing type select
        hoodieButton.setOnAction(e -> ShopMenu.updateChoice(ClothingUtility.HOODIE));
        longShirtButton.setOnAction(e -> ShopMenu.updateChoice(ClothingUtility.LONG_SHIRT));
        sweatShirtButton.setOnAction(e -> ShopMenu.updateChoice(ClothingUtility.SWEATSHIRT));
        tankTopButton.setOnAction(e -> ShopMenu.updateChoice(ClothingUtility.TANK_TOP));
        tShirtButton.setOnAction(e -> ShopMenu.updateChoice(ClothingUtility.SHORT_SHIRT));


        //On image button click, run addImage method to choose image file
        imageButton.setOnAction(e -> {
            try {
                filePath.setText(ShopMenu.addImage());
                if(filePath.getText() != null){

                    //Get filepath and replace "\" with "\\"
                    filePath.setText(filePath.getText().replace("\\", "\\\\"));

                    //Create new image based on filepath
                    ShopMenu.logo.setImage((new Image(new FileInputStream(filePath.getText()))));
                    ShopMenu.imageChosen = logo.getImage();

                    //Adjust image size and location
                    if(logo.getImage().getWidth()/logo.getImage().getHeight() < 0.6){
                        ShopMenu.logo.setFitWidth(Common.MAX_IMAGE_SIZE *
                            (logo.getImage().getWidth()/logo.getImage().getHeight()));
                    }
                    else{
                        ShopMenu.logo.setFitWidth(Common.MAX_IMAGE_SIZE);
                    }
                    ShopMenu.logo.setPreserveRatio(true);
                    ShopMenu.logo.setX((shirtBorder.getWidth() - logo.getFitWidth())/2);
                    ShopMenu.logo.setY(235);

                    //Make image visible
                    ShopMenu.logo.setVisible(true);
                    filePath.setVisible(true);
                }
            } catch (Exception ex) {
                AlertBox.display(ex);
                filePath.setVisible(false);
            }
        });


        //On account button click, display and change control to Login
        loginButton.setOnAction(e -> {
            LoginMenu.displayLoginMenu();
            MainMenu.updateUserStatus();
        });


        //Add listener for changes in price
        quantityBox.textProperty().addListener((observable, oldQty, newQty) -> {

            try{
                if(newQty.compareTo("") != 0){
                    int qty = Integer.parseInt(newQty);
                    ShopMenu.quantityChosen = qty;
                    DecimalFormat df = new DecimalFormat("0.00");
                    totalPrice.setText(String.valueOf("$"
                        + df.format(qty * Double.parseDouble(unitPrice.getText()))));
                }
            }
            catch(NumberFormatException ex){
                quantityChosen = 0;
                quantityBox.setText("");
                totalPrice.setText("$0.00");
            }
        });


        //On remove image button click
        removeButton.setOnAction(e -> {
            ShopMenu.logo.setImage(null);
            ShopMenu.imageChosen = null;
            filePath.setVisible(false);
        });


        //On reset button click clear all values on shopping menu
        resetButton.setOnAction(e -> ShopMenu.resetShopMenu(sizeComboBox, quantityBox, filePath));


        //On color option click, change color of prototype shirt
        scene.setOnMouseClicked(e -> {
             if(e.getTarget() instanceof Circle){
                 ShopMenu.applyColor(((Circle) e.getTarget()).getFill());
                 e.consume();
             }
        });


        //On size select
        sizeComboBox.setOnAction(e -> {
                ShopMenu.sizeChosen = ClothingUtility.convertClothingType(
                    sizeComboBox.getValue());
        });


        //Add listener for changes in quantity
        unitPrice.textProperty().addListener((observable, oldPrice, newPrice) -> {


            if(quantityBox.getText().compareTo("") == 0 || Integer.parseInt(quantityBox.getText()) == 0){
                totalPrice.setText("$0.00");
            }
            try{
                double price = Double.parseDouble(newPrice);
                DecimalFormat df = new DecimalFormat("0.00");
                totalPrice.setText(String.valueOf("$"
                    + df.format(price * quantityChosen)));
            }
            catch(NumberFormatException ex){
                totalPrice.setText("$0.00");
            }
        });
    }


    //**** <<<<<<<<<<<<< HELPER METHODS FOR DISPLAY SHOPPING >>>>>>>>>>>>> ****
    /**
     * Helper method to build clothing type buttons within gridpane for STEP 1
     * @param _text
     * @param _filename
     * @return
     */
    public static Button helpBuildClothingButton(String _text, String _filename){

        ImageView imageView = new ImageView(new Image(_filename));
        Button button = new Button(_text, imageView);
        button.setContentDisplay(TOP);
        return button;
    }


     /**
     * Helper method to add formatting to the Clothing GridPane STEP 1
     * @return:  formatted GridPane
     */
    public static GridPane helpBuildGridClothing(){

        GridPane gridClothing = new GridPane();
        gridClothing.setPadding(new Insets(0,0,0,34));
        gridClothing.setHgap(Common.STANDARD_HGAP);
        gridClothing.setVgap(5);

        //center align each node in the gridpane
        for(int i = 0; i < 5; i++){
            ColumnConstraints[] col = new ColumnConstraints[5];
            col[i] = new ColumnConstraints();
            col[i].setHalignment(HPos.CENTER);
            gridClothing.getColumnConstraints().add(col[i]);
        }
        return gridClothing;
    }


    /**
     * Helper method to build circle matrix of color options (STEP 3)
     * @param _colorOption
     * @return:  formatted GridPane
     */
    public static GridPane helpBuildGridColors(Circle[] _colorOption){

        GridPane gridColors = new GridPane();
        gridColors.setPadding(new Insets(0,0,0,34));
        gridColors.setHgap(15);
        gridColors.setVgap(15);

        //set spacing between circles in gridpane
        for(int i = 0; i < ClothingUtility.COLUMN_COUNT; i++){
            ColumnConstraints[] col = new ColumnConstraints[ClothingUtility.COLUMN_COUNT];
            col[i] = new ColumnConstraints(40);
            col[i].setHalignment(HPos.CENTER);
            gridColors.getColumnConstraints().add(col[i]);
        }

        //add colored circles to GridPane
        int i = 0;
        for(int r = 0; r < ClothingUtility.ROW_COUNT; r++){
            for(int c = 0; c < ClothingUtility.COLUMN_COUNT; c++){
                _colorOption[i] = new Circle();
                _colorOption[i].setRadius(15);
                _colorOption[i].setStroke(Color.BLACK);
                _colorOption[i].setStrokeWidth(1.5);
                _colorOption[i].setFill(ClothingUtility.AVAILABLE_COLORS[i]);

                gridColors.add(_colorOption[i], c, r);
                i++;
            }
        }
        return gridColors;
    }


    /**
     * Helper method to build the instruction steps for display
     * @param _number
     * @param _description
     * @return group
     */
    public static Group helpBuildInstructionSteps(String _number, String _description){

        //Build left rectangle and add step number
        Rectangle rectNumber = new Rectangle();
        rectNumber.setHeight(34);
        rectNumber.setWidth(34);
        rectNumber.setFill(Color.CADETBLUE);
        Text rectNumberText = new Text(_number);
        rectNumberText.setFill(Color.WHITE);
        rectNumberText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        //Build right rectangle and add step descrtiption
        Rectangle rectDescription = new Rectangle();
        rectDescription.setHeight(34);
        rectDescription.setWidth(545);
        rectDescription.setFill(Color.DARKSALMON);
        Text rectDescriptionText = new Text(_description);
        rectDescriptionText.setFill(Color.BLACK);
        rectDescriptionText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        //Pane for left half
        StackPane leftPane = new StackPane();
        leftPane.getChildren().addAll(rectNumber, rectNumberText);

        //Pane for right right
        StackPane rightPane = new StackPane();
        rightPane.getChildren().addAll(rectDescription, rectDescriptionText);

        //HBox to combine left and right
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20,0,5,0));
        hBox.getChildren().addAll(leftPane, rightPane);

        Group group = new Group();
        group.getChildren().addAll(hBox);
        return group;
    }


    /**
     * Helper method to build price text for clothing type within gridpane STEP 1
     * @param _price: price of the clothing item
     * @return: formatted Text of the clothing item price
     */
    public static Text helpBuildPrices(double _price){

        Text text = new Text("$" + String.valueOf(_price));
        text.setFont(Font.font(null, FontWeight.BOLD, 18));
        return text;
    }


    /**
     * Helper method to build and formate rectangle displaying prototype
     * @return:  formatted rectangle
     */
    public static Rectangle helpBuildShirtBorder(){

        Rectangle shirtBorder = new Rectangle();
        shirtBorder.setStroke(Color.BLACK);
        shirtBorder.setStrokeWidth(3);
        shirtBorder.setHeight(640);
        shirtBorder.setWidth(475);
        shirtBorder.setFill(Color.ANTIQUEWHITE);

        return shirtBorder;
    }


    /**
     * Helper method add ComboBox and populate with data STEP 2
     * @return:  formatted ComboBox
     */
    public static ComboBox<String> helpBuildSizeComboBox(){

        ComboBox<String> sizeComboBox = new ComboBox<>();
        sizeComboBox.setPrefWidth(170);
        for(String e: ClothingUtility.SIZE_NAMES)
            sizeComboBox.getItems().add(e);
        sizeComboBox.setPromptText("Choose Size");

        return sizeComboBox;
    }


    /**
     * method to reset shopping menu form, clear out all current selections
     * @param _sizeComboBox
     * @param _quantityBox
     * @param _filePath
     */
    public static void resetShopMenu(ComboBox<String> _sizeComboBox, TextField _quantityBox, Text _filePath){

        clothingChosen = sizeChosen = quantityChosen = 0;
        colorChosen = null;
        priceChosen = 0.0;
        imageChosen = null;
        path.setContent("");
        path.setFill(Color.WHITE);
        back.setContent("");
        back.setFill(Color.WHITE);
        unitPrice.setText("0.00");
        totalPrice.setText("$0.00");
        logo.setVisible(false);
        _sizeComboBox.setValue("");
        _quantityBox.setText("");
        _filePath.setVisible(false);
        _filePath.setText("File:  ");
    }


    /**
     * method to apply settings upon clothing type choice
     * @param _choice
     */
    public static void updateChoice(int _choice){

        switch (_choice) {

            case ClothingUtility.TANK_TOP:
                clothingChosen = ClothingUtility.TANK_TOP;
                path.setContent(ClothingUtility.TANK_TOP_PATH);
                back.setContent(ClothingUtility.TANK_TOP_BACKPATH);
                priceChosen = ClothingUtility.TANK_TOP_PRICE;
                unitPrice.setText(String.valueOf(ClothingUtility.TANK_TOP_PRICE));
                break;

            case ClothingUtility.SHORT_SHIRT:
                clothingChosen = ClothingUtility.SHORT_SHIRT;
                path.setContent(ClothingUtility.SHORT_SHIRT_PATH);
                back.setContent(ClothingUtility.SHORT_SHIRT_BACKPATH);
                priceChosen = ClothingUtility.SHORT_SHIRT_PRICE;
                unitPrice.setText(String.valueOf(ClothingUtility.SHORT_SHIRT_PRICE));
                break;

            case ClothingUtility.LONG_SHIRT:
                clothingChosen = ClothingUtility.LONG_SHIRT;
                path.setContent(ClothingUtility.LONG_SHIRT_PATH);
                back.setContent(ClothingUtility.LONG_SHIRT_BACKPATH);
                priceChosen = ClothingUtility.LONG_SHIRT_PRICE;
                unitPrice.setText(String.valueOf(ClothingUtility.LONG_SHIRT_PRICE));
                break;

            case ClothingUtility.SWEATSHIRT:
                clothingChosen = ClothingUtility.SWEATSHIRT;
                path.setContent(ClothingUtility.SWEATSHIRT_PATH);
                back.setContent(ClothingUtility.SWEATSHIRT_BACKPATH);
                priceChosen = ClothingUtility.SWEATSHIRT_PRICE;
                unitPrice.setText(String.valueOf(ClothingUtility.SWEATSHIRT_PRICE));
                break;

            case ClothingUtility.HOODIE:
                ShopMenu.clothingChosen = ClothingUtility.HOODIE;
                path.setContent(ClothingUtility.HOODIE_PATH);
                back.setContent(ClothingUtility.HOODIE_BACKPATH);
                priceChosen = ClothingUtility.HOODIE_PRICE;
                unitPrice.setText(String.valueOf(ClothingUtility.HOODIE_PRICE));
                break;

            default:
                break;
        }
    }
}
