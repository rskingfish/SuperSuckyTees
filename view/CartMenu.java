package view;

/**
 * CartMenu is the class for displaying the shopping cart.  It provides the user
 * with a table of items in the shopping cart, the shipping costs associated
 * with the cart contents, the grand total for the order.
 * @author rsking
 * @version 11/27/18
 */

import Controllers.CartController;
import Controllers.OrderController;
import Controllers.ShippingController;
import Models.Cart;
import Models.TableList;
import Models.User;
import Utility.Common;
import Utility.Tools;
import java.text.DecimalFormat;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class CartMenu {

    protected static  Text shippingAmount = new Text();
    protected static Text subTotalAmount = new Text();
    protected static Text grandTotalAmount = new Text();


    /**
     * Method to build the body text order details for the confirmation email
     * @return
     */
    public static String buildOrderDetails(int _orderID) {

        DecimalFormat df = new DecimalFormat("0.00");

        String top = "\n\nPurchaser:= " + ((User.getCurrentUser().getFullName() == null)? User.getCurrentUser().getIDNum() : User.getCurrentUser().getFullName()) + "\n"
                + "Order Number:= " + _orderID + "\n"
                + "Order Date:= " + String.valueOf(java.time.LocalDate.now()) + "\n\n\n";

        String middle = "";
        for (Cart cart : Cart.viewCart(User.getCurrentUser().getIDNum())) {
            middle += cart.toString() + "\n"
                + "Total:= $" + String.valueOf(df.format(cart.getProduct().getPrice() * cart.getAmount())) + "\n\n";
        }

        String bottom = "Order subTotal:= " + subTotalAmount.getText() + "\n"
                + "Shipping Charges:= " + shippingAmount.getText() + "\n"
                + "Order Total:= " + grandTotalAmount.getText();

        return top + middle + bottom;
    }


    //Method to build cart menu and display to user
    public static void displayCart() {


        //Add company name across top
        HBox companyBanner = MainMenu.helpBuildCompanyBanner();
        companyBanner.setAlignment(Pos.TOP_CENTER);


        //Add order number
        Text orderNumber = new Text("Cart Contents For User:= " + User.getCurrentUser().getUserName());
        orderNumber.setFont(Font.font(null, FontWeight.BOLD, 18));


        //Add column for clothing type
        TableColumn <TableList, String> clothingColumn = new TableColumn<>("Clothing Type");
        clothingColumn.setMinWidth(160);
        clothingColumn.setStyle("-fx-alignment: CENTER;");
        clothingColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        //Add column for clothing size
        TableColumn <TableList, String> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setMinWidth(120);
        sizeColumn.setStyle("-fx-alignment: CENTER;");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));

        //Add column for clothing color
        TableColumn <TableList, String> colorColumn = new TableColumn<>("Color Selected");
        colorColumn.setMinWidth(160);
        colorColumn.setStyle("-fx-alignment: CENTER;");
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

        //Add column for unit price
        TableColumn <TableList, Double> priceColumn = new TableColumn<>("Unit Price");
        priceColumn.setMinWidth(110);
        priceColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Add column for quantity purchased
        TableColumn <TableList, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setEditable(true);

        //Add column for whether item includes image
        TableColumn <TableList, Boolean> imageColumn = new TableColumn<>("Optional Image Added");
        imageColumn.setMinWidth(150);
        imageColumn.setStyle("-fx-alignment: CENTER;");
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("includesImage"));


        //Add table view and populate with list of items in cart
        TableView <TableList> table = new TableView<>();
        table.setItems(TableList.getTableList());

        table.getColumns().addAll(clothingColumn, sizeColumn, colorColumn, priceColumn, quantityColumn, imageColumn);


        //Add order information in the center
        VBox orders = new VBox(Common.STANDARD_VGAP);
        orders.setAlignment(Pos.TOP_CENTER);
        orders.setPadding(new Insets(100,200,200,200));
        orders.setMaxHeight(600);
        orders.getChildren().addAll(orderNumber, table);

        //Add subTotal, shipping, and Total
        GridPane totals = helpBuildTotals(subTotalAmount, shippingAmount, grandTotalAmount);

        //Add orders and totals to StackPane
        StackPane centerPane = new StackPane();
        centerPane.getChildren().addAll(orders, totals);

        //Add bottom row of buttons
        HBox bottomToolbar = new HBox(Common.STANDARD_HGAP);
        bottomToolbar.setAlignment(Pos.BOTTOM_CENTER);
        bottomToolbar.setPadding(new Insets(10,10,20,10));

        ImageView imageView = new ImageView(new Image("images/ContinueShoppingIcon.png"));
        Button continueButton = new Button("CONTINUE\nSHOPPING", imageView);
        continueButton.setFont(Font.font(null, FontWeight.BOLD, 13));

        Button blankSpaceLeft = new Button();
        blankSpaceLeft.setPrefSize(40, 40);
        blankSpaceLeft.setVisible(false);

        imageView = new ImageView(new Image("images/PrinterButtonIcon.png"));
        Button printButton = new Button("PRINT\nCART", imageView);
        printButton.setFont(Font.font(null, FontWeight.BOLD, 13));

        imageView = new ImageView(new Image("images/EmptyCartIcon.png"));
        Button emptyButton = new Button("EMPTY CART\nCONTENTS", imageView);
        emptyButton.setFont(Font.font(null, FontWeight.BOLD, 13));

        imageView = new ImageView(new Image("images/ShippingButtonIcon.png"));
        Button shippingButton = new Button("CALCULATE\nSHIPPING", imageView);
        shippingButton.setFont(Font.font(null, FontWeight.BOLD, 13));

        Button blankSpaceRight = new Button();
        blankSpaceRight.setPrefSize(40, 40);
        blankSpaceRight.setVisible(false);

        imageView = new ImageView(new Image("images/CheckOutIcon.png"));
        Button checkoutButton = new Button("COMPLETE\nCHECKOUT", imageView);
        checkoutButton.setFont(Font.font(null, FontWeight.BOLD, 13));

        bottomToolbar.getChildren().addAll(continueButton, blankSpaceLeft,
                printButton, emptyButton, shippingButton, blankSpaceRight, checkoutButton);


        //Add scene to stage and show stage
        BorderPane layout = new BorderPane();
        layout.setTop(companyBanner);
        layout.setCenter(centerPane);
        layout.setBottom(bottomToolbar);

        Scene scene = new Scene(layout, Common.DISPLAY_WIDTH, Common.DISPLAY_HEIGHT);
        Stage cart = new Stage();
        cart.setTitle("Cart Menu");
        cart.initModality(Modality.APPLICATION_MODAL);
        cart.setScene(scene);
        cart.setResizable(false);
        cart.setX(MainMenu.getMainMenuX());
        cart.setY(MainMenu.getMainMenuY());
        cart.show();


        //************* <<<<<<<<<<<<< ACTIONS >>>>>>>>>>>>> **************
        //On check button click
        checkoutButton.setOnAction(e -> {
            OrderController.finalizeOrder(User.getCurrentUser().getIDNum(),
                    parseCurrency(subTotalAmount.getText()),
                    parseCurrency(shippingAmount.getText()), 0);
            int orderID = OrderController.identifyOrderID(User.getCurrentUser().getIDNum());
            Tools.emailOrderConfirmation(CartMenu.buildOrderDetails(orderID));
            if(CartController.finalize(User.getCurrentUser().getIDNum(), orderID)) {
                AlertBox.display("Your order has been successfully processed.\n"
                        + "A confirmation email will be sent.", "Order Confirmation");
            };
            cart.close();
        });


        //On continue button click, change control back to Shop Menu
        continueButton.setOnAction(e -> {
            ShopMenu.displayShopping();
            cart.close();
        });


        //Add listener for changes in shipping
        shippingAmount.textProperty().addListener((observable, oldShip, newShip) -> {

            double val1 = parseCurrency(newShip);
            double val2 = parseCurrency(subTotalAmount.getText());
            Double grand = val1 + val2;
            DecimalFormat df = new DecimalFormat("0.00");
            grandTotalAmount.setText(String.valueOf("$" + df.format(grand)));
        });


        //Add listener for changes in subtotal
        subTotalAmount.textProperty().addListener((observable, oldSub, newSub) -> {

            double val1 = parseCurrency(newSub);
            double val2 = parseCurrency(shippingAmount.getText());
            Double grand = val1 + val2;
            DecimalFormat df = new DecimalFormat("0.00");
            grandTotalAmount.setText(String.valueOf("$" + df.format(grand)));
        });


        //On empty cart button click, remove all user's items from cart
        emptyButton.setOnAction(e -> {
            CartController.clearCart(User.getCurrentUser().getIDNum());
            AlertBox.display(Alert.AlertType.INFORMATION,
                    "Your cart has been cleared of all items.\n"
                    + "You will now be returned to the Shop Menu.",
                    "Empty Cart Confirmation", "Empty Cart");
            ShippingController.resetRushShipping();
            ShopMenu.displayShopping();
            cart.close();
        });


        //On print button click, print current cart
        printButton.setOnAction((ActionEvent e) ->{

            PrinterJob printJob = PrinterJob.createPrinterJob();
            if(printJob.showPrintDialog(cart.getOwner()) && printJob.printPage(table))
            printJob.endJob();
        });


        //On shipping button click, change control to Shipping Controller
        shippingButton.setOnAction(e -> ShippingController.updateShippingCosts());
    }


    //**** <<<<<<<<<<<<< HELPER METHODS FOR DISPLAY CART MENU >>>>>>>>>>>>> ****
    /**
     * Helper method to build and format totals GridPane
     * @param _subTotalAmount
     * @param _shippingAmount
     * @param _grandTotalAmount
     * @return
     */
    public static GridPane helpBuildTotals(Text _subTotalAmount, Text _shippingAmount, Text _grandTotalAmount) {

        DecimalFormat df = new DecimalFormat("0.00");
        double tempTotal = 0.0;

         for(TableList t : TableList.getTableList()) {
            tempTotal += t.getPrice() * t.getQuantity();
        }

        Text subTotal = new Text("SubTotal Cart: ");
        subTotal.setFont(Font.font(null, FontWeight.BOLD, 18));

        //Set subtotal amount based on sum of all items in cart
        _subTotalAmount.setText(String.valueOf("$" + df.format(tempTotal)));
        _subTotalAmount.setFill(Color.BLUE);
        _subTotalAmount.setFont(Font.font(null, FontWeight.BOLD, 18));

        Text shipping = new Text("Shipping Charges: ");
        shipping.setFont(Font.font(null, FontWeight.BOLD, 18));

        //Set shipping amount based on subtotal amount in cart
        _shippingAmount.setText("$0.00");
        updateShippingAmount();
        _shippingAmount.setFill(Color.BLUE);
        _shippingAmount.setFont(Font.font(null, FontWeight.BOLD, 18));

        Text grandTotal = new Text("GRAND TOTAL: ");
        grandTotal.setFont(Font.font(null, FontWeight.BOLD, 24));

        //Format grand total and populate with sum of subtotal and shipping
        double ship = parseCurrency(_shippingAmount.getText());
        double sub = parseCurrency(_subTotalAmount.getText());
        double grand = ship + sub;

        _grandTotalAmount.setText(String.valueOf("$" + df.format(grand)));
        _grandTotalAmount.setFill(Color.BLUE);
        _grandTotalAmount.setFont(Font.font(null, FontWeight.BOLD, 24));

        GridPane totals = new GridPane();
        totals.setAlignment(Pos.BOTTOM_RIGHT);
        totals.setHgap(10);
        totals.setVgap(5);
        totals.setPadding(new Insets(10, 350, 80, 10));
        totals.addRow(0, subTotal, _subTotalAmount);
        totals.addRow(1, shipping, _shippingAmount);
        totals.addRow(2, grandTotal, _grandTotalAmount);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.RIGHT);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHalignment(HPos.RIGHT);
        totals.getColumnConstraints().addAll(col1, col2);

        return totals;
    }


    public static double parseCurrency(String _text) {

        _text = _text.replace("$", "");
        Double d = Double.parseDouble(_text);
        return d;
    }


    public static void updateShippingAmount() {

        //Get subtotal value and convert to usable double
        String text = subTotalAmount.getText();
        text = text.replace("$", "");
        Double sub = Double.parseDouble(text);
        Double ship = 0.0;

        //Set shipping charge based on subtotal shipping gates
        if(sub < 0.10) {
            ship = 0.0;
        } else if (sub < 50.00) {
            ship = 7.95;
        } else if(sub < 100.00) {
            ship = 10.95;
        } else if(sub < 150.00) {
            ship = 13.95;
        } else if(sub < 200.00) {
            ship = 16.95;
        }

        //Add possible surcharge for rush option
        ship += ShippingController.getRushShipping();

        //Format output correctly
        DecimalFormat df = new DecimalFormat("0.00");
        shippingAmount.setText(String.valueOf("$" + df.format(ship)));
    }
}
