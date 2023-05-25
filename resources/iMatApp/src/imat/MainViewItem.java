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
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class MainViewItem extends AnchorPane {

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
    private Product product;
    private MainViewItemDetail mainViewItemDetail;
    private int amount = 0;
    @FXML
    private ImageView itemImageView;
    @FXML
    private Text itemNameText;
    @FXML
    private Text itemPriceLabel;
    @FXML
    private TextField itemAmountTextField;
    @FXML
    private AnchorPane minusButton;
    @FXML
    private AnchorPane plusButton;


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
        itemAmountTextField.setText(Integer.toString(amount));

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
    }

    // -- Methods -- //

    public void addItemToShoppingCart() {
        shoppingCart.addProduct(product);
        int amount = Integer.valueOf(itemAmountTextField.getText());
        amount += 1;
        itemAmountTextField.setText(Integer.toString(amount));
    }

    public void removeItemFromShoppingCart() {
        shoppingCart.removeProduct(product);
        int amount = Integer.valueOf(itemAmountTextField.getText());
        if (amount >= 1) {
            amount -= 1;
        }
        itemAmountTextField.setText(Integer.toString(amount));
    }
    public void setItemInShoppingCart() {
        int amount = Integer.valueOf(itemAmountTextField.getText());
        if (amount >= 0) {
            shoppingCart.removeItem(new ShoppingItem(product));
            Double new_amount = Double.valueOf(amount);
            shoppingCart.addProduct(product, new_amount);
        }
    }

}



