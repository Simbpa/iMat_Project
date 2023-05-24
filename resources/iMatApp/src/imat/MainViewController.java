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

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MainViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private Map<String, MainViewItem> mainViewItemMap = new HashMap<String, MainViewItem>();
    List<Product> productList =  iMatDataHandler.getProducts();
    private static MainViewController instance = null;

    // -- Methods -- //

    private static synchronized MainViewController getInstance() {
        if (instance == null) {
            instance = new MainViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private MainViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("main_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        mainViewInitialize();

        // Button Actions

        mainViewBasketCloseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //hideMainViewBasket();
            }
        });
    }

    // -- FXML objects -- //
    @FXML
    private FlowPane mainViewFlowPane;
    @FXML
    private AnchorPane mainViewBasket;
    @FXML
    private Button mainViewToBasketButton;
    @FXML
    private Button mainViewBasketCloseButton;



    // -- Methods -- //

    public void mainViewInitialize() {
        populateItemMap();
        populateMainView();
    }
    public void populateItemMap() {
        for (Product product: productList) {
            MainViewItem mainViewItem = new MainViewItem(product);
            mainViewItemMap.put(product.getName(), mainViewItem);
        }
    }
    public void populateMainView() {
        mainViewFlowPane.getChildren().clear();
        for (Product product : productList) {
            mainViewFlowPane.getChildren().add(mainViewItemMap.get(product.getName()));
        }
    }

    public void showMainViewBasket() {
        mainViewBasket.toFront();
    }
    public void hideMainViewBasket() {
        mainViewBasket.toBack();
    }
}
