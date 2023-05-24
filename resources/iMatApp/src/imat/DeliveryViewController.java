package imat;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class DeliveryViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static DeliveryViewController instance = null;

    // -- Methods -- //

    private static synchronized DeliveryViewController getInstance() {
        if (instance == null) {
            instance = new DeliveryViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private DeliveryViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("delivery_view.fxml"));
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
