package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCartListener;
import se.chalmers.cse.dat216.project.ShoppingItem;

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
                ApplicationController.getInstance().switchPage(DeliveryViewController.getPage());
            }
        });
        paymentViewPayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(saveInfoBox.isSelected()){
                    if(!year.getText().isBlank() || !year.getText().isBlank()) {
                        IMatDataHandler.getInstance().getCreditCard().setValidYear(Integer.valueOf(year.getText()));
                    }
                    if(!month.getText().isBlank() || !month.getText().isBlank()) {
                        IMatDataHandler.getInstance().getCreditCard().setValidMonth(Integer.valueOf(month.getText()));
                    }
                    if(!cvc.getText().isBlank() || !cvc.getText().isBlank()) {
                        IMatDataHandler.getInstance().getCreditCard().setVerificationCode(Integer.valueOf(cvc.getText()));
                    }
                    if(!cardNumber.getText().isBlank() || !cardNumber.getText().isBlank()) {
                        IMatDataHandler.getInstance().getCreditCard().setCardNumber(cardNumber.getText());
                    }
                }

                ConfirmationViewController.getInstance().setFinalPrice(IMatDataHandler.getInstance().getShoppingCart().getTotal());
                ConfirmationViewController.getInstance().setAdressField();
                for(ShoppingItem item : IMatDataHandler.getInstance().getShoppingCart().getItems()){
                    MainViewController.getInstance().mainViewItemMap.get(item.getProduct().getName()).clearedBasket();
                }
                IMatDataHandler.getInstance().placeOrder();
                IMatDataHandler.getInstance().shutDown();
                MainViewController.getInstance().populateMainViewBasket();
                BasketViewController.getInstance().populateBasketViewBasket();
                ApplicationController.getInstance().switchPage(ConfirmationViewController.getPage());
                AccountViewController.getInstance().initHistoryView();

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
    @FXML
    private TextField cvc;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField month;
    @FXML
    private TextField year;
    @FXML
    private CheckBox saveInfoBox;


}
