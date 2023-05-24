package imat;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class paymentViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static paymentViewController instance = null;

    // -- Methods -- //

    private static synchronized paymentViewController getInstance() {
        if (instance == null) {
            instance = new paymentViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private paymentViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("payment_view.fxml"));
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
