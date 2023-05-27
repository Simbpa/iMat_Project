package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.util.ArrayList;

public class ConfirmationViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static ConfirmationViewController instance = null;

    // -- Methods -- //

    public static synchronized ConfirmationViewController getInstance() {
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
        confirmationViewToMainViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });

    }
    public void setFinalPrice(double amount){
        amountField.setText(Double.toString(amount));
    }
    public void setAdressField(){
        ArrayList<String> oi = ShowAccountViewController.getInstance().getOrderInformation();
        adressField.setText(oi.get(0));
    }
    // -- FXMl Objects -- //
    @FXML
    private Label amountField;
    @FXML
    private Label adressField;
    @FXML
    private Button confirmationViewToMainViewButton;
}
