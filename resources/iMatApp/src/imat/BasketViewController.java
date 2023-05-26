// This module serves as the controller for the main view

// -- Packages -- //
package imat;

// -- Imports -- //
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BasketViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static BasketViewController instance = null;

    // -- Methods -- //

    public static synchronized BasketViewController getInstance() {
        if (instance == null) {
            instance = new BasketViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private BasketViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("basket_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Button Actions

        basketToMainViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });

        basketToLoginViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(LoginViewController.getPage());
            }
        });

    }

    // -- FXML-Object -- //
    @FXML
    private Button basketToMainViewButton;
    @FXML
    private Button basketToLoginViewButton;
    @FXML
    private FlowPane basketFlowPane;

    // -- Methods -- //

    public void populateBasketViewBasket() {
        basketFlowPane.getChildren().clear();
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        for (ShoppingItem shoppingItem : shoppingCart.getItems()) {
            BasketItem basketItem = new BasketItem(shoppingItem.getProduct(), shoppingItem.getAmount());
            basketFlowPane.getChildren().add(basketItem);
        }
    }
}