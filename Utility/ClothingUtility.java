package Utility;


/**
 * Utility class for various clothing attributes, SVGPath for clothing
 * prototype images, enumerations, and data values.
 * @author rsking
 * @version 11/25/18
 */

import javafx.scene.paint.Color;


public class ClothingUtility {

    //Array of text verbage for size choice
    public static final String[] SIZE_NAMES = {
        "(S) Small", "(M) Medium", "(L) Large", "(LT) Large Tall",
        "(XL) XLarge", "(XLT) XLarge Tall", "(XXL) 2X Large", "(XB) XBig"
    };


    //Array of available clothing colors
    public static Color[] AVAILABLE_COLORS = {
        Color.WHITE, Color.RED, Color.CORNFLOWERBLUE, Color.GRAY, Color.YELLOW,
        Color.MEDIUMSEAGREEN, Color.TOMATO, Color.CADETBLUE, Color.BISQUE,
        Color.GOLD, Color.DEEPPINK, Color.AQUAMARINE, Color.GREEN, Color.AQUA,
        Color.DARKGRAY, Color.FUCHSIA, Color.GOLDENROD, Color.VIOLET,
        Color.CRIMSON, Color.CHARTREUSE, Color.DARKKHAKI, Color.ORANGE,
        Color.LIGHTGREEN, Color.MAROON, Color.NAVY, Color.LIMEGREEN,
        Color.DODGERBLUE, Color.GAINSBORO, Color.OLIVE, Color.BLACK
    };


    //Array of available clothing colors in string array
    public static String[] COLOR_NAMES = {"WHITE", "RED", "CORNFLOWERBLUE",
        "GRAY","YELLOW", "MEDIUMSEAGREEN", "TOMATO", "CADETBLUE", "BISQUE",
        "GOLD", "DEEPPINK", "AQUAMARINE", "GREEN", "AQUA", "DARKGRAY",
        "FUCHSIA", "GOLDENROD", "VIOLET","CRIMSON", "CHARTREUSE", "DARKKHAKI",
        "ORANGE", "LIGHTGREEN", "MAROON", "NAVY", "LIMEGREEN",
        "DODGERBLUE", "GAINSBORO", "OLIVE", "BLACK"};


    //Integers defining how the available colors will be displayed
    public static final int ROW_COUNT = 3;
    public static final int COLUMN_COUNT = 10;


    //Strings defining SVGPath for shirt types and visible shirt backs
    public static String TANK_TOP_PATH = "M189,135 L169,135 "
            + "A198.8112,198.8112 0, 0, 1, 128.5,282 "
            + "L128.5,575 L346.5,575 L346.5,282 "
            + "A198.8112,198.8112 0, 0, 1, 306,135 "
            + "L286,135 L286,168 "
            + "A48.5,48.5 0, 0, 1, 189,168 Z";
    public static String SHORT_SHIRT_PATH = "M108.5,159 L83.5,183 L36.5,327 "
            + "L127.5,339 L128.5,282 L128.5,575 L346.5,575 L346.5,282 "
            + "L347.5,339 L438.5,327 L391.5,183 L366.5,159 L286.5,135"
            + "A49,35 0, 0, 1, 188.5,135 Z";
    public static String LONG_SHIRT_PATH = "M108.5,159 L83.5,183 L36.5,327 "
            + "L27.5,547 L68.5,547 L128.5,282 L128.5,575 L346.5,575 L346.5,282 "
            + "L406.5,547 L447.5,547 L438.5,327 L391.5,183 L366.5,159 "
            + "L286.5,135 A49,35 0, 0, 1, 188.5,135 Z";
    public static String SWEATSHIRT_PATH = "M128.5,432.824 L128.5,575 "
            + "L346.5,575 L346.5,282 L364.5,335 L355.5,536 L394.5,542 "
            + "L438.5,327 L391.5,183 L366.5,159 L286.5,135 "
            + "A49,35 0, 0, 1, 188.5,135 L108.5,159 L83.5,183 L53,278 L53,344 "
            + "L180.5,494 L211.5,469 L116.5,317 L128.5,282 L128.5,336.2";
    public static String HOODIE_PATH = "M108.5,159 L83.5,183 L36.5,327 "
            + "L80.5,542 L119.5,536 L110.5,335 L128.5,282 L128.5,575 "
            + "L346.5,575 L346.5,282 L364.5,335 L355.5,536 L394.5,542 "
            + "L438.5,327 L391.5,183 L366.5,159 L286.5,135 L237.5,225 "
            + "L188.5,135 Z";
    public static String TANK_TOP_BACKPATH = "M286,135 L286,168 "
            + "A48.5,48.5 0, 0, 1, 189,168 L189,135 A48.5,35 0, 0, 0, 286,135";
    public static String SHORT_SHIRT_BACKPATH = "M286.5,135 "
            + "A49,35 0, 0, 1, 188.5,135 A49,10 0, 0, 0, 286.5,135";
    public static String LONG_SHIRT_BACKPATH = "M286.5,135 "
            + "A49,35 0, 0, 1, 188.5,135 A49,10 0, 0, 0, 286.5,135";
    public static String SWEATSHIRT_BACKPATH = "M286.5,135 "
            + "A49,35 0, 0, 1, 188.5,135 A49,10 0, 0, 0, 286.5,135";
    public static String HOODIE_BACKPATH = "M286.5,135 L237.5,225 L188.5,135 "
            + "A30.2,30.2 0, 0, 1, 160.5,92 "
            + "A100,40 0, 0, 1, 314.5,92"
            + "A30.2,30.2 0, 0, 1, 286.5,135";


    //double representing unit price for each clothing type
    public static final double TANK_TOP_PRICE = 3.95;
    public static final double SHORT_SHIRT_PRICE = 7.61;
    public static final double LONG_SHIRT_PRICE = 9.18;
    public static final double SWEATSHIRT_PRICE = 17.44;
    public static final double HOODIE_PRICE = 20.99;


    //Integer value representing each size available
    public static final int SMALL = 1;
    public static final int MEDIUM = 2;
    public static final int LARGE = 3;
    public static final int LARGE_TALL = 4;
    public static final int XL = 5;
    public static final int XL_TALL = 6;
    public static final int XXL = 7;
    public static final int XB = 8;


    //Integer value representing each type of clothing
    public static final int TANK_TOP = 1;
    public static final int SHORT_SHIRT = 2;
    public static final int LONG_SHIRT = 3;
    public static final int SWEATSHIRT = 4;
    public static final int HOODIE = 5;


    //Shipping weight for a small article of clothing, need for shipping calcs
    public static final double TANK_TOP_BASE = 4.6;
    public static final double SHORT_SHIRT_BASE = 5.0;
    public static final double LONG_SHIRT_BASE = 6.2;
    public static final double SWEATSHIRT_BASE = 9.2;
    public static final double HOODIE_BASE = 9.5;


    //Weight increment for each increase in clothing size, needing for shipping calcs
    public static final double TANK_TOP_UPSIZE = 0.15;
    public static final double SHORT_SHIRT_UPSIZE = 0.50;
    public static final double LONG_SHIRT_UPSIZE = 0.55;
    public static final double SWEATSHIRT_UPSIZE = 0.90;
    public static final double HOODIE_UPSIZE = 0.95;


    /**
     * Utility method to convert ComboBox String to integer equivalent
     * @param _size:  String value from sizeComboBox on ShopMenu
     * @return: corresponding integer for same clothing size
     */
    public static int convertClothingType(String _size){

        switch(_size){
            case "(S) Small":
                return SMALL;
            case "(M) Medium":
                return MEDIUM;
            case "(L) Large":
                return LARGE;
            case "(LT) Large Tall":
                return LARGE_TALL;
            case "(XL) XLarge":
                return XL;
            case "(XLT) XLarge Tall":
                return XL_TALL;
            case "(XXL) 2X Large":
                return XXL;
            default:
                return XB;
        }
    }


    /**
     * Method used to convert an integer representing clothing type to a
     * string description
     * @param _type
     * @return
     */
    public static String getDescription(int _type){

        switch(_type){
            case 1:
                return "TANK TOP";
            case 2:
                return "SHORT SHIRT";
            case 3:
                return "LONG SHIRT";
            case 4:
                return "SWEATSHIRT";
            default:
                return "HOODIE";
        }
    }


    /**
     * Method used to convert an integer representing clothing size to a
     * string description
     * @param _type
     * @return
     */
    public static String getSizeName(int _type){

        switch(_type){
            case 1:
                return "SMALL";
            case 2:
                return "MEDIUM";
            case 3:
                return "LARGE";
            case 4:
                return "LARGE TALL";
            case 5:
                return "XL";
            case 6:
                return "XL TALL";
            case 7:
                return "XXL";
            default:
                return "XB";
        }
    }


    /**
     * Method returns the string name of the color instead of hex value
     * @param _color
     * @return
     */
    public static String getColorDescription(String _color) {

        int i = 0;
        while(_color.compareTo(AVAILABLE_COLORS[i].toString()) != 0 && i < AVAILABLE_COLORS.length) {
            i++;
        }
        return COLOR_NAMES[i];
    }
}
