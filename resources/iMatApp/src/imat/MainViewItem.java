package imat;

// -- Imports -- //
import com.sun.tools.javac.Main;
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
    public String colour = "White";
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
    @FXML
    private AnchorPane backgroundAnchorPane;
    @FXML
    private Button mainViewItemFavouriteButton;


    // -- Constructor -- //
    public MainViewItem(Product product){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main_view_item_2.fxml"));
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
        mainViewItemFavouriteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (colour == "White") {
                    colour = "Red";
                    mainViewItemFavouriteButton.setStyle("-fx-background-color: red;" +  "-fx-border-color: red;");
                    iMatDataHandler.addFavorite(product);
                }
                else if (colour == "Red") {
                    colour = "White";
                    mainViewItemFavouriteButton.setStyle("-fx-background-color: white;" +  "-fx-border-color: #D2D2D2;");
                    iMatDataHandler.removeFavorite(product);
                }

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
                    backgroundAnchorPane.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1;");
                    itemAmountTextField.setStyle("-fx-background-color: white");
                    iMatDataHandler.getShoppingCart().removeProduct(product);
                }
                else{
                    backgroundAnchorPane.setStyle("-fx-background-color: #A2D085; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1;");
                    itemAmountTextField.setStyle("-fx-background-color: #A2D085");


                }

                itemAmountTextField.setText(Integer.toString(amount));
            }

        }

    }
    public void recipeDetail(){
        MainViewController.getInstance().displayDetailView(product, this);
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

    }
    public void setItemInShoppingCart() {
        int current_amount = Integer.valueOf(itemAmountTextField.getText());
        int difference = current_amount - amount;
        amount = current_amount;
        iMatDataHandler.getShoppingCart().addProduct(product, difference);
        BasketViewController.getInstance().populateBasketViewBasket();
        MainViewController.getInstance().populateMainViewBasket();
    }

    public void increaseAmount() {
        amount += 1;
    }
    public void clearedBasket(){
        amount = 0;
        itemAmountTextField.setText(Integer.toString(amount));
    }
    public void decreaseAmount() {
        amount -= 1;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int i) {
        amount = i;
    }
}



