package imat;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class showAccountViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static showAccountViewController instance = null;

    // -- Methods -- //

    private static synchronized showAccountViewController getInstance() {
        if (instance == null) {
            instance = new showAccountViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private showAccountViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("show_account_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Button Actions

    }

    // -- FXML Objects -- //


}
