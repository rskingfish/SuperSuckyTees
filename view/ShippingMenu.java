package view;


/**
 * ShippingMenu displays the shipping menu and corresponding shipping costs,
 * which are displayed to the user on the CartMenu.
 * @author rsking
 * @version 11/27/18
 */

import Utility.Common;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ShippingMenu {

    private static double rushOption = 0.0;

    public static void displayShippingMenu() {

        //Add VBox to hold all nodes
        VBox pane = new VBox(Common.STANDARD_VGAP);
        pane.setPadding(new Insets(20, 20, 20, 20));
        pane.setAlignment(Pos.CENTER);

        //Add shipping title
        Text shippingText = new Text("Shipping Options and Prices");
        shippingText.setFont(Font.font(null, FontWeight.BOLD, 18));

        //Add information text
        Text information = new Text("Shipping charges are automatically "
                + "determined based on the total amount of the items in your "
                + "cart.  However, you have the option to upgrade to expedited "
                + "shipping by clicking the 'rush opton' below.");
        information.setWrappingWidth(360);

        //Add checkbox for rush shipping option
        CheckBox rush = new CheckBox("Add Rush Shipping Option");
        if(rushOption > 1) {
            rush.setSelected(true);
        }

        //Add button to close
        Button closeButton = new Button("OK");
        closeButton.setFont(Font.font(null, FontWeight.BOLD, 16));
        closeButton.setMinSize(100, 50);

        //Add column for spending gates
        TableColumn<Rate, String> gateColumn = new TableColumn<>("Cart Total");
        gateColumn.setMinWidth(120);
        gateColumn.setSortable(false);
        gateColumn.setCellValueFactory(new PropertyValueFactory<>("gate"));

        //Add column for standard delivery rates
        TableColumn<Rate, String> standardColumn = new TableColumn<>("Standard Delivery");
        standardColumn.setMinWidth(130);
        standardColumn.setSortable(false);
        standardColumn.setCellValueFactory(new PropertyValueFactory<>("standard"));
        standardColumn.setStyle( "-fx-alignment: CENTER;");

        //Add column for rush delivery rates
        TableColumn<Rate, String> rushColumn = new TableColumn<>("Rush Delivery");
        rushColumn.setMinWidth(128);
        rushColumn.setSortable(false);
        rushColumn.setCellValueFactory(new PropertyValueFactory<>("rush"));
        rushColumn.setStyle("-fx-alignment: CENTER-RIGHT;"
                + "-fx-text-fill: red;"
                + "-fx-font-weight: bold;"
        );

        //List of shipping rate gates
        final ObservableList<Rate> rates = FXCollections.observableArrayList(
                new Rate("   Under $49.99", "$7.95", "$11.95        "),
                new Rate("   $50.00 - $99.99", "$10.95", "$14.95        "),
                new Rate("   $100.00 - $149.99", "$13.95", "$17.95        "),
                new Rate("   $150.00 - $199.99", "$16.95", "$20.95        "),
                new Rate("   Over $200.00", "FREE", "$4.00        ")
        );

        //Add table view with list of shipping options
        TableView <Rate> table = new TableView<>();
        table.setItems(rates);
        table.setEditable(false);
        table.getColumns().addAll(gateColumn, standardColumn, rushColumn);

        //Add all nodes to the pane
        pane.getChildren().addAll(shippingText, table, information, rush, closeButton);

        //Build scene and stage
        Scene scene = new Scene(pane, 410, 390);
        Stage shippingStage = new Stage();
        shippingStage.setTitle("Shipping Charges");
        shippingStage.initModality(Modality.APPLICATION_MODAL);
        shippingStage.setScene(scene);
        shippingStage.setResizable(false);
        shippingStage.show();


        //************* <<<<<<<<<<<<< ACTIONS >>>>>>>>>>>>> **************
        //On shop button click, change control to Shop Menu
        closeButton.setOnAction(e -> {
            CartMenu.updateShippingAmount();
            shippingStage.close();
        });

        //On rush shipping option click, add $4.00 to overall shipping cost
        rush.setOnAction(e -> {
            rushOption = rush.isSelected() ? 4.0 : 0.0;
        });
    }


    public static double getRushOption() {
        return rushOption;
    }


    /**
     * Limited use object used to display rates
     */
    public static class Rate {

        private final String gate;
        private final String standard;
        private final String rush;

        Rate(String _gate, String _standard, String _rush) {
            this.gate = _gate;
            this.standard = _standard;
            this.rush = _rush;
        }


        public String getGate() {
            return gate;
        }


        public String getStandard() {
            return standard;
        }


        public String getRush() {
            return rush;
        }


        @Override
        public String toString() {
            return "{Ship Gates:=" + gate
                + ", Standard:=" + standard
                + ", Rush:=" + rush + "}";
        }
    }


    public static void resetRushOption() {
        rushOption = 0.0;
    }
}
