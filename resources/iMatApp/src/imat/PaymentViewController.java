package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

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
        if(IMatDataHandler.getInstance().getShoppingCart().getTotal() != 0){
            double cartSum = IMatDataHandler.getInstance().getShoppingCart().getTotal();
            totalSumLabel.setText(Double.toString(cartSum) + " kr");
            finalPriceLabel.setText(Double.toString(cartSum + 70) + " kr");
        }
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                double cartSum = IMatDataHandler.getInstance().getShoppingCart().getTotal();
                totalSumLabel.setText(Double.toString(cartSum) + " kr");
                finalPriceLabel.setText(Double.toString(cartSum + 70) + " kr");
            }
        });
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
                IMatDataHandler.getInstance().placeOrder();
                AccountViewController.getInstance().initHistoryView();
                ApplicationController.getInstance().switchPage(ConfirmationViewController.getPage());
            }
        });

    }

    // -- FXML Objects -- //
    @FXML
    private Label finalPriceLabel;
    @FXML
    private Label totalSumLabel;
    @FXML
    private Button paymentViewToMainViewButton;
    @FXML
    private Button paymentViewBackButton;
    @FXML
    private Button paymentViewPayButton;

}
