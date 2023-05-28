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
import se.chalmers.cse.dat216.project.*;

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
    private TextField itemAmountTextField;
    @FXML
    private Button minusButton;
    @FXML
    private Button plusButton;


    // -- Constructor -- //
    public MainViewBasketItem(Product product, Double given_amount){
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
        this.amount = given_amount.intValue();


        itemImageView.setImage(iMatDataHandler.getFXImage(product, 65, 65));
        itemNameLabel.setText(product.getName());
        itemPriceLabel.setText(Double.toString(product.getPrice()));
        itemAmountTextField.setText(Integer.toString(amount));

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

        // Shopping Cart Listener

        iMatDataHandler.getShoppingCart().addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                updateItem(cartEvent.getShoppingItem());
            }
        });
    }

    // -- Methods -- //
    public void recipeDetail(){
        MainViewController.getInstance().displayDetailView(product, MainViewController.getInstance().mainViewItemMap.get(product.getName()));
    }

    public void updateItem(ShoppingItem shoppingItem) {
        amount = MainViewController.getInstance().getMainViewItemMap().get(product.getName()).getAmount();
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
        MainViewController.getInstance().mainViewItemMap.get(product.getName()).increaseAmount();
        iMatDataHandler.getShoppingCart().addProduct(product);
        BasketViewController.getInstance().populateBasketViewBasket();
        MainViewController.getInstance().populateMainViewBasket();
    }

    public void removeItemFromShoppingCart() {
        MainViewController.getInstance().mainViewItemMap.get(product.getName()).decreaseAmount();
        iMatDataHandler.getShoppingCart().addProduct(product, -1);
        BasketViewController.getInstance().populateBasketViewBasket();
        MainViewController.getInstance().populateMainViewBasket();
    }
}
    /*public void setItemInShoppingCart() {
        int amount = Integer.valueOf(itemAmountLabel.getText());
        if (amount >= 0) {
            shoppingCart.removeItem(new ShoppingItem(product));
            Double new_amount = Double.valueOf(amount);
            shoppingCart.addProduct(product, new_amount);

        }
        MainViewController.getInstance().populateMainViewBasket();
    }*/





