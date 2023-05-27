// This module serves as the controller for the main view

// -- Packages -- //
package imat;

// -- Imports -- //
import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MainViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public Map<String, MainViewItem> mainViewItemMap = new HashMap<String, MainViewItem>();
    List<Product> productList =  iMatDataHandler.getProducts();
    private static MainViewController instance = null;
    public Map<String, MainViewItem> getMainViewItemMap(){
        return mainViewItemMap;
    }
    private Product detailProduct;
    private MainViewItem detailItem;

    // -- Methods -- //

    public static synchronized MainViewController getInstance() {
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
        sumLabel.setText("0 kr");
        mainViewInitialize();
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                sumLabel.setText(Double.toString(IMatDataHandler.getInstance().getShoppingCart().getTotal()) + " kr");
            }
        });

        // Button Actions

        mainViewBasketCloseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hideMainViewBasket();
            }
        });
        clearBasketButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(MainViewItem item :mainViewItemMap.values()){
                    item.clearedBasket();
                }
                IMatDataHandler.getInstance().getShoppingCart().clear();
                BasketViewController.getInstance().populateBasketViewBasket();
                MainViewController.getInstance().populateMainViewBasket();
            }
        });
        mainViewToBasketButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainViewController.getInstance().populateMainViewBasket();
                ApplicationController.getInstance().switchPage(BasketViewController.getPage());

            }
        });
        frukt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<Product> list_one = IMatDataHandler.getInstance().getProducts(ProductCategory.FRUIT);
                list_one.addAll(IMatDataHandler.getInstance().getProducts(ProductCategory.VEGETABLE_FRUIT));
                list_one.addAll(IMatDataHandler.getInstance().getProducts(ProductCategory.ROOT_VEGETABLE));
                list_one.addAll(IMatDataHandler.getInstance().getProducts(ProductCategory.EXOTIC_FRUIT));
                list_one.addAll(IMatDataHandler.getInstance().getProducts(ProductCategory.CITRUS_FRUIT));
                list_one.addAll(IMatDataHandler.getInstance().getProducts(ProductCategory.MELONS));
                list_one.addAll(IMatDataHandler.getInstance().getProducts(ProductCategory.BERRY));
                list_one.addAll(IMatDataHandler.getInstance().getProducts(ProductCategory.CABBAGE));
                productList = list_one;
                populateMainView();
            }
        });
        fisk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                productList = IMatDataHandler.getInstance().getProducts(ProductCategory.FISH);
                populateMainView();
            }
        });
        meat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                productList = IMatDataHandler.getInstance().getProducts(ProductCategory.MEAT);
                populateMainView();
            }
        });
        mejeri.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                productList= IMatDataHandler.getInstance().getProducts(ProductCategory.DAIRIES);
                populateMainView();
            }
        });
        drinks.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<Product> list_one = IMatDataHandler.getInstance().getProducts(ProductCategory.COLD_DRINKS);
                list_one.addAll(IMatDataHandler.getInstance().getProducts(ProductCategory.HOT_DRINKS));
                productList = list_one;
                populateMainView();
            }
        });
        skafferi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<Product> list_one = IMatDataHandler.getInstance().getProducts(ProductCategory.PASTA);
                list_one.addAll(IMatDataHandler.getInstance().getProducts(ProductCategory.POD));
                list_one.addAll(IMatDataHandler.getInstance().getProducts(ProductCategory.POTATO_RICE));
                productList = list_one;
                populateMainView();
            }
        });
        kryddor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                List<Product> list_one = IMatDataHandler.getInstance().getProducts(ProductCategory.HERB);
                list_one.addAll(IMatDataHandler.getInstance().getProducts(ProductCategory.FLOUR_SUGAR_SALT));
                productList = list_one;
                populateMainView();
            }
        });
        bread.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                productList = IMatDataHandler.getInstance().getProducts(ProductCategory.BREAD);
                populateMainView();
            }
        });
        sweet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                productList = IMatDataHandler.getInstance().getProducts(ProductCategory.SWEET);
                populateMainView();
            }
        });
        allt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                productList = IMatDataHandler.getInstance().getProducts();
                populateMainView();
            }
        });
    }

    public void displayDetailView(Product product, MainViewItem item){
        this.detailProduct = product;
        this.detailItem = item;
        itemDetailAmountField.setText(Integer.toString(item.getAmount()));
        if(item.getAmount() > 0){
            itemDetailAmountPane.setStyle("-fx-background-color: #A2D085; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1;");
        }
        else{
            itemDetailAmountPane.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1;");
        }
        itemDetailImage.setImage(IMatDataHandler.getInstance().getFXImage(product));
        itemDetailInformation.setText("En helt underbar vara");
        itemDetailNameLabel.setText(product.getName());
        itemDetailPriceLabel.setText(Double.toString(product.getPrice()));
        itemDetailView.toFront();
    }

    public void closeDetailView(){
        itemDetailView.toBack();
    }

    public void updateItem(ShoppingItem shoppingItem) {
        double amount = MainViewController.getInstance().getMainViewItemMap().get(detailProduct.getName()).getAmount();
        if (shoppingItem != null) {
            if (shoppingItem.getProduct() == detailProduct) {
                if (amount <= 0) {
                    amount = 0;
                    iMatDataHandler.getShoppingCart().removeProduct(detailProduct);
                }
                itemDetailAmountField.setText(Double.toString(amount));
            }
        }
    }
    public void addItemToShoppingCart() {
        MainViewController.getInstance().mainViewItemMap.get(detailProduct.getName()).increaseAmount();
        iMatDataHandler.getShoppingCart().addProduct(detailProduct);
        displayDetailView(detailProduct, detailItem);
        BasketViewController.getInstance().populateBasketViewBasket();
        MainViewController.getInstance().populateMainViewBasket();
    }

    public void removeItemFromShoppingCart() {
        MainViewController.getInstance().mainViewItemMap.get(detailProduct.getName()).decreaseAmount();
        iMatDataHandler.getShoppingCart().addProduct(detailProduct, -1);
        displayDetailView(detailProduct, detailItem);
        BasketViewController.getInstance().populateBasketViewBasket();
        MainViewController.getInstance().populateMainViewBasket();
    }

    // -- FXML objects -- //
    @FXML
    private AnchorPane itemDetailAmountPane;
    @FXML
    private TextField itemDetailAmountField;
    @FXML
    private AnchorPane itemDetailView;
    @FXML
    private ImageView itemDetailImage;
    @FXML
    private Label itemDetailNameLabel;
    @FXML
    private Label itemDetailPriceLabel;
    @FXML
    private TextArea itemDetailInformation;




    @FXML
    private Button allt;
    @FXML
    private Button frukt;
    @FXML
    private Button fisk;
    @FXML
    private Button mejeri;
    @FXML
    private Button meat;
    @FXML
    private Button drinks;
    @FXML
    private Button skafferi;
    @FXML
    private Button sweet;
    @FXML
    private Button bread;
    @FXML
    private Button kryddor;



    @FXML
    private Button clearBasketButton;
    @FXML
    private Label sumLabel;
    @FXML
    private FlowPane mainViewFlowPane;
    @FXML
    private AnchorPane mainViewBasket;
    @FXML
    private Button mainViewToBasketButton;
    @FXML
    private Button mainViewBasketCloseButton;
    @FXML
    private FlowPane mainViewBasketFlowPane;



    // -- Methods -- //


    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

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

    public void populateMainViewBasket() {
        mainViewBasketFlowPane.getChildren().clear();
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        for (ShoppingItem shoppingItem : shoppingCart.getItems()) {
            MainViewBasketItem mainViewBasketItem = new MainViewBasketItem(shoppingItem.getProduct(), shoppingItem.getAmount());
            mainViewBasketFlowPane.getChildren().add(mainViewBasketItem);
        }
    }
}
