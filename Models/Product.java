package Models;


/**
 * Class defines a Product Object.  An instance of a Product is the combination
 * of clothing type, size, color, and price.
 * @author rsking
 * @version 11/25/18
 */

import Utility.ClothingUtility;


public class Product {

    private static Product currentProduct;

    private int clothingType;
    private int size;
    private String color;
    private double price;


    /**
     * Full constructor for creating new instance of a Product
     * @param _clothingType: integer designating clothing type
     * @param _size: integer designating clothing size re: ClothingSize
     * @param _color: color of clothing item
     * @param _price:  unit price of the clothing item
     */
    public Product(int _clothingType, int _size, String _color, double _price) {

        this.clothingType = _clothingType;
        this.size = _size;
        this.color = _color;
        this.price = _price;

        currentProduct = this;
    }


    //Empty constructor
    public Product() {
    }


    public int getClothingType() {
        return this.clothingType;
    }


    public String getColor() {
        return this.color;
    }


    public static Product getCurrentProduct(){
        return currentProduct;
    }


    public double getPrice() {
        return this.price;
    }


    public int getSize() {
        return this.size;
    }


    public void setClothingType(int _clothingType) {
        this.clothingType = _clothingType;
    }


    public void setColor(String _color) {
        this.color = _color;
    }


    public static void setCurrentProduct(Product _product){
        currentProduct = _product;
    }


    public void setPrice(double _price) {
        this.price = _price;
    }


    public void setSize(int _size) {
        this.size = _size;
    }


    @Override
    public String toString() {
        return "{Item:=" + ClothingUtility.getDescription(this.clothingType)
                + ", Size:=" + ClothingUtility.SIZE_NAMES[this.size - 1]
                + ", Color:=" + this.color
                + ", Price:=" + this.price
                + "}";
    }
}