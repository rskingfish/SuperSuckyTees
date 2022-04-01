package ShirtShop;


/**
 * ShirtShop is the project Application. This class handles start session.
 * @author rsking
 * @version 11/25/18
 */

import view.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;


public class ShirtShop extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        MainMenu.displayMainMenu(stage);
    }
}
