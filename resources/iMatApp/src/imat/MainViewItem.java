package imat;

// -- Imports -- //
import imat.MainViewItemDetail;
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
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.List;

public class MainViewItem extends AnchorPane {

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    private int amount = 0;
    private Product product;
    private MainViewItemDetail mainViewItemDetail;
    @FXML
    private ImageView itemImageView;
    @FXML
    private Text itemNameText;
    @FXML
    private Text itemPriceLabel;
    @FXML
    private TextField itemAmountTextField;
    @FXML
    private Button minusButton;
    @FXML
    private Button plusButton;


    // -- Constructor -- //
    public MainViewItem(Product product){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main_view_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;
        this.mainViewItemDetail = new MainViewItemDetail(product);

        itemImageView.setImage(iMatDataHandler.getFXImage(product));
        itemNameText.setText(product.getName());
        itemPriceLabel.setText(Double.toString(product.getPrice()));
        itemAmountTextField.setText(Integer.toString(0));

        // Button Actions

        itemAmountTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setItemInShoppingCart();
            }
        });
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

        // Add listener to Shopping Cart
        iMatDataHandler.getShoppingCart().addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                updateItem(cartEvent.getShoppingItem());
            }
        });
    }

    // -- Methods -- //

    public void updateItem(ShoppingItem shoppingItem) {
        if (shoppingItem != null) {
            if (shoppingItem.getProduct() == product) {
                if (amount <= 0) {
                    amount = 0;
                    iMatDataHandler.getShoppingCart().removeProduct(product);
                }
                itemAmountTextField.setText(Integer.toString(amount));
            }
        }

    }
    public void addItemToShoppingCart() {
        amount += 1;
        iMatDataHandler.getShoppingCart().addProduct(product);
        BasketViewController.getInstance().populateBasketViewBasket();
        MainViewController.getInstance().populateMainViewBasket();
    }

    public void removeItemFromShoppingCart() {

        amount -= 1;
        iMatDataHandler.getShoppingCart().addProduct(product, -1);
        BasketViewController.getInstance().populateBasketViewBasket();
        MainViewController.getInstance().populateMainViewBasket();

        /*
        int amount = 0;
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        for (ShoppingItem shoppingItem: shoppingCart.getItems()) {
            if (shoppingItem.getProduct() == product) {
                amount = (int) shoppingItem.getAmount();
                if (amount > 1) {

                    //shoppingItem.setAmount(amount-1);
                    iMatDataHandler.getShoppingCart().fireShoppingCartChanged(shoppingItem, true);
                } else {
                    iMatDataHandler.getShoppingCart().removeProduct(product);
                }
            }
        }
         */
    }
    public void setItemInShoppingCart() {
        int amount = Integer.valueOf(itemAmountTextField.getText());
        if (amount >= 0) {
            iMatDataHandler.getShoppingCart().removeItem(new ShoppingItem(product));
            Double new_amount = Double.valueOf(amount);
            iMatDataHandler.getShoppingCart().addProduct(product, new_amount);

        }
    }

    public void increaseAmount() {
        amount += 1;
    }
    public void clearedBasket(){amount = 0;}
    public void decreaseAmount() {
        amount -= 1;
    }
    public int getAmount() {
        return amount;
    }
}



