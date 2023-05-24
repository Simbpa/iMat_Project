package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class PaymentViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static PaymentViewController instance = null;

    // -- Methods -- //

    private static synchronized PaymentViewController getInstance() {
        if (instance == null) {
            instance = new PaymentViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private PaymentViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("payment_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Button Actions
        paymentViewToMainViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });
        paymentViewBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(ShowAccountViewController.getPage());
            }
        });
        paymentViewPayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(ConfirmationViewController.getPage());
            }
        });

    }

    // -- FXML Objects -- //
    @FXML
    private Button paymentViewToMainViewButton;
    @FXML
    private Button paymentViewBackButton;
    @FXML
    private Button paymentViewPayButton;

}
