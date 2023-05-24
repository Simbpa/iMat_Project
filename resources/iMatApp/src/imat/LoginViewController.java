package imat;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class LoginViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static LoginViewController instance = null;

    // -- Methods -- //

    private static synchronized LoginViewController getInstance() {
        if (instance == null) {
            instance = new LoginViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private LoginViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("login_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Button Actions

    }
}
