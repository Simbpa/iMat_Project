package imat;

// -- Imports -- //
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

public class MainViewBasketItem extends AnchorPane {

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
    private Product product;
    private MainViewItemDetail mainViewItemDetail;
    private int amount = 0;
    @FXML
    private ImageView itemImageView;
    @FXML
    private Label itemNameLabel;
    @FXML
    private Label itemPriceLabel;
    @FXML
    private Label itemAmountLabel;
    @FXML
    private Button minusButton;
    @FXML
    private Button plusButton;


    // -- Constructor -- //
    public MainViewBasketItem(Product product){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main_view_basket_item.fxml"));
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
        itemNameLabel.setText(product.getName());
        itemPriceLabel.setText(Double.toString(product.getPrice()));
        itemAmountLabel.setText(Integer.toString(amount));

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
        shoppingCart.addProduct(product);
        int amount = Integer.valueOf(itemAmountLabel.getText());
        amount += 1;
        itemAmountLabel.setText(Integer.toString(amount));
        MainViewController.getInstance().populateMainViewBasket();
    }

    public void removeItemFromShoppingCart() {
        shoppingCart.removeProduct(product);
        int amount = Integer.valueOf(itemAmountLabel.getText());
        if (amount >= 1) {
            amount -= 1;
        }
        itemAmountLabel.setText(Integer.toString(amount));
        MainViewController.getInstance().populateMainViewBasket();
    }
    public void setItemInShoppingCart() {
        int amount = Integer.valueOf(itemAmountLabel.getText());
        if (amount >= 0) {
            shoppingCart.removeItem(new ShoppingItem(product));
            Double new_amount = Double.valueOf(amount);
            shoppingCart.addProduct(product, new_amount);
        }
        MainViewController.getInstance().populateMainViewBasket();

    }

}




