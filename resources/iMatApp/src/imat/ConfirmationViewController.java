package imat;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class ConfirmationViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static ConfirmationViewController instance = null;

    // -- Methods -- //

    private static synchronized ConfirmationViewController getInstance() {
        if (instance == null) {
            instance = new ConfirmationViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private ConfirmationViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("confirmation_view.fxml"));
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
