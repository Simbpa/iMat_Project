package imat;

// -- Imports -- //
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class BasketItem extends AnchorPane {

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
    private ShoppingItem product;
    private int amount;
    @FXML
    private Label basketItemSum;
    @FXML
    private ImageView itemImageView;
    @FXML
    private Label itemNameLabel;
    @FXML
    private Label itemPriceLabel;
    @FXML
    private TextField itemAmountLabel;
    @FXML
    private Button minusButton;
    @FXML
    private Button plusButton;


    // -- Constructor -- //
    public BasketItem(ShoppingItem product){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("basket_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        this.amount = (int) product.getAmount();

        itemImageView.setImage(iMatDataHandler.getFXImage(product.getProduct()));
        itemNameLabel.setText(product.getProduct().getName());
        itemPriceLabel.setText(Double.toString(product.getProduct().getPrice()) + " kr");
        itemAmountLabel.setText(Integer.toString(amount));
        basketItemSum.setText(Double.toString(amount * product.getProduct().getPrice()) + " kr");



        itemAmountLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (newValue != null && !newValue.isEmpty() && !newValue.isBlank()) {
                    System.out.println(Double.valueOf(shoppingCart.getTotal()));
                    System.out.println("here");
                    if (Double.valueOf(newValue) <= 0) {
                        shoppingCart.removeItem(product);
                        MainViewController.getInstance().populateMainViewBasket();
                        return;
                    }
                    double v = Double.valueOf(newValue) - product.getAmount();
                    if(Double.valueOf(newValue) > 0 ) {
                        shoppingCart.addProduct(product.getProduct(), v);
                    }
                    else{
                        for(int i = 0; i < v; i++) {
                            shoppingCart.removeProduct(product.getProduct());
                        }
                    }
                    System.out.println(Double.valueOf(shoppingCart.getTotal()));
                    MainViewController.getInstance().populateMainView();
                    MainViewController.getInstance().populateMainViewBasket();
                    BasketViewController.getInstance().populateMainViewBasket();

                }
            }
        });


                                                       // Button Actions
        minusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removeItemFromShoppingCart();
            }
        });
        plusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addItemToShoppingCart();
            }
        });
    }

    // -- Methods -- //


    public void addItemToShoppingCart() {
        shoppingCart.addProduct(product.getProduct());
        int amount = (int) product.getAmount();
        itemAmountLabel.setText(Integer.toString(amount));
        MainViewController.getInstance().populateMainView();
        MainViewController.getInstance().populateMainViewBasket();
        BasketViewController.getInstance().populateMainViewBasket();

    }

    public void removeItemFromShoppingCart() {
        System.out.println(IMatDataHandler.getInstance().getShoppingCart().getTotal());
        int amount = (int) product.getAmount();
        if (amount <= 1) {
            shoppingCart.removeItem(product);
        }
        else{
            amount -= 1;
            shoppingCart.addProduct(product.getProduct(), -1);

            itemAmountLabel.setText(Integer.toString(amount));
        }
        System.out.println(IMatDataHandler.getInstance().getShoppingCart().getTotal());
        MainViewController.getInstance().populateMainView();
        MainViewController.getInstance().populateMainViewBasket();
        BasketViewController.getInstance().populateMainViewBasket();

    }
}




