package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class ShowAccountViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static ShowAccountViewController instance = null;

    // -- Methods -- //

    private static synchronized ShowAccountViewController getInstance() {
        if (instance == null) {
            instance = new ShowAccountViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private ShowAccountViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("show_account_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Button Actions
        showAccountViewtoMainViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });

        showAccountViewBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(BasketViewController.getPage());
            }
        });
        showAccountViewNextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(DeliveryViewController.getPage());
            }
        });

    }

    // -- FXML Objects -- //
    @FXML
    private Button showAccountViewtoMainViewButton;
    @FXML
    private Button showAccountViewBackButton;
    @FXML
    private Button showAccountViewNextButton;

}
