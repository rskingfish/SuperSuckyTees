package Models;


/**
 * Limited use object used to display cart details in tableView.  Only getters
 * used/needed to build tableView.
 * @author rsking
 * @version 12-2-18
 */

import Utility.ClothingUtility;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class TableList {


    private final SimpleStringProperty type;
    private final SimpleStringProperty size;
    private final SimpleStringProperty color;
    private final SimpleDoubleProperty price;
    private final SimpleIntegerProperty quantity;
    private final SimpleBooleanProperty includesImage;


    //Constructor
    TableList(String _type, String _size, String _color, double _price, int _quantity, boolean _includesImage) {
        this.type = new SimpleStringProperty(_type);
        this.size = new SimpleStringProperty(_size);
        this.color = new SimpleStringProperty(_color);
        this.price = new SimpleDoubleProperty(_price);
        this.quantity = new SimpleIntegerProperty(_quantity);
        this.includesImage = new SimpleBooleanProperty(_includesImage);
    }


    public String getColor() {
        return color.get();
    }


    public double getPrice() {
        return price.get();
    }


    public int getQuantity() {
        return quantity.get();
    }


    public String getSize() {
        return size.get();
    }


    public String getType() {
        return type.get();
    }


    public boolean getIncludesImage() {
        return includesImage.get();
    }


    public boolean isIncludesImage() {
        return includesImage.get();
    }


    public static ObservableList<TableList> getTableList() {

        ObservableList<TableList> tableList = FXCollections.observableArrayList();

        //combine orders and products into a single table list
        List<Cart> cartList = Cart.viewCart(User.getCurrentUser().getIDNum());

        for (Cart c : cartList) {
            tableList.add(new TableList(
                    ClothingUtility.getDescription(c.getProduct().getClothingType()),
                    ClothingUtility.getSizeName(c.getProduct().getSize()),
                    ClothingUtility.getColorDescription(c.getProduct().getColor()),
                    c.getProduct().getPrice(),
                    c.getAmount(),
                    c.includesImage()
            ));
        }
        return tableList;
    }
}
