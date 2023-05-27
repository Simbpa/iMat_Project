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
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
        initCategories();
        initCategories2();
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("basket_view.fxml"));
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
        // Button Actions
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                double cartSum = IMatDataHandler.getInstance().getShoppingCart().getTotal();
                totalSumLabel.setText(Double.toString(cartSum) + " kr");
                finalPriceLabel.setText(Double.toString(cartSum + 70) + " kr");
            }
        });
        basketToMainViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });

        basketToLoginViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(ApplicationController.getInstance().isLogged_in());
                if(ApplicationController.getInstance().isLogged_in()){
                    ApplicationController.getInstance().switchPage(ShowAccountViewController.getPage());

                }
                else {
                    ApplicationController.getInstance().switchPage(LoginViewController.getPage());
                }
            }
        });

    }

    // -- FXML-Object -- //
    @FXML
    private Label finalPriceLabel;
    @FXML
    private Label totalSumLabel;
    @FXML
    private Button basketToMainViewButton;
    @FXML
    private Button basketToLoginViewButton;
    @FXML
    private FlowPane basketFlowPane;

    // -- Methods -- //

    // -- Methods -- //
    private Map<Integer, String> categoryMap2 = new HashMap<Integer, String>();

    private void initCategories2() {
        categoryMap2.put(0, "Frukt & Grönt");
        categoryMap2.put(1, "Fisk");
        categoryMap2.put(2, "Mejeri & Ägg");
        categoryMap2.put(3, "Kött, Fågel & Chark");
        categoryMap2.put(4, "Dryck");
        categoryMap2.put(5, "Skafferi");
        categoryMap2.put(6, "Bröd");
        categoryMap2.put(7, "Godis & Nötter");
        categoryMap2.put(8, "Kryddor");
    }
    private Map<String, Integer> categoryMap = new HashMap<String, Integer>();
    private void initCategories() {
        categoryMap.put("EXOTIC_FRUIT", 0);
        categoryMap.put("CABBAGE", 0);
        categoryMap.put("CITRUS_FRUITS", 0);
        categoryMap.put("BERRY", 0);
        categoryMap.put("VEGETABLE_FRUIT", 0);
        categoryMap.put("ROOT_VEGETABLE", 0);
        categoryMap.put("MELONS", 0);
        categoryMap.put("FRUIT", 0);
        categoryMap.put("FISH", 1);
        categoryMap.put("DAIRIES", 2);
        categoryMap.put("MEAT", 3);
        categoryMap.put("COLD_DRINKS", 4);
        categoryMap.put("HOT_DRINKS", 4);
        categoryMap.put("PASTA", 5);
        categoryMap.put("POTATO_RICE", 5);
        categoryMap.put("POD", 5);
        categoryMap.put("BREAD", 6);
        categoryMap.put("NUTS_AND_SEEDS", 7);
        categoryMap.put("SWEET", 7);
        categoryMap.put("FLOUR_SUGAR_SALT", 8);
        categoryMap.put("HERB", 8);
    }


    private Collection<TitledPane> createPanes(ArrayList<ArrayList<ShoppingItem>> basketItems){
        Collection<TitledPane> result = new ArrayList<TitledPane>();
        for(ArrayList<ShoppingItem> productsInCategory : basketItems){
            if(productsInCategory.size() >= 1) {
                customTitledPane2 tp = new customTitledPane2(categoryMap2.get(categoryMap.get(productsInCategory.get(0).getProduct().getCategory().name())) + "              Styckpris");
                tp.setText("Antal         Totalt pris");
                for (ShoppingItem item : productsInCategory) {
                    BasketItem basketItem = new BasketItem(item.getProduct(), item.getAmount());
                    tp.theFlowPane.getChildren().add(basketItem);
                }
                tp.setContent(tp.theFlowPane);
                result.add(tp);
            }
        }
        return result;
    }
    public ArrayList<ArrayList<ShoppingItem>> categorizeBasket(){
        ArrayList<ArrayList<ShoppingItem>> categorizedBasket = new ArrayList<ArrayList<ShoppingItem>>();
        for(int i = 0; i<=20;i++){
            categorizedBasket.add(new ArrayList<ShoppingItem>());
        }
        for(ShoppingItem item : IMatDataHandler.getInstance().getShoppingCart().getItems()){
            categorizedBasket.get(categoryMap.get(item.getProduct().getCategory().name())).add(item);
        }
        return categorizedBasket;
    }

    public void populateBasketViewBasket() {
        /*basketFlowPane.getChildren().clear();
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        for (ShoppingItem shoppingItem : shoppingCart.getItems()) {
            BasketItem basketItem = new BasketItem(shoppingItem.getProduct(), shoppingItem.getAmount());
            basketFlowPane.getChildren().add(basketItem);
        }*/
        basketFlowPane.getChildren().clear();
        VBox noaccordion = new VBox();
        noaccordion.getChildren().addAll(this.createPanes(categorizeBasket()));
        basketFlowPane.getChildren().add(noaccordion);
    }
}