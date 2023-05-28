// This module serves as the main controller of the program

// -- Packages -- //
package imat;


// -- Imports -- //

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import se.chalmers.cse.dat216.project.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class ApplicationController extends AnchorPane {

    // -- General Attributes -- //
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    private boolean logged_in = false;
    private static ApplicationController instance = null;
    private ArrayList<ArrayList<ShoppingItem>> shoppingLists= new ArrayList<ArrayList<ShoppingItem>>();
    private HashMap<Integer, String> shoppingListNames = new HashMap<Integer, String>();


    public static synchronized ApplicationController getInstance() {
        return instance;
    }


    public ApplicationController() {
        ArrayList<ShoppingItem> test = new ArrayList<ShoppingItem>();
        Random rand = new Random();
        for(int i = 1; i<10; i++){
            ShoppingItem test2 = new ShoppingItem(IMatDataHandler.getInstance().getProduct(i), rand.nextDouble()*10);
            test.add(test2);
        }
        addShoppingList(test, "Söndag");
        test = new ArrayList<ShoppingItem>();
        rand = new Random();
        for(int i = 11; i<35; i++){
            ShoppingItem test2 = new ShoppingItem(IMatDataHandler.getInstance().getProduct(i), rand.nextDouble()*10);
            test.add(test2);
        }
        addShoppingList(test, "Storhandling");

        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("imat_app.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        instance = this;

        switchPage(MainViewController.getPage());
        AccountViewController.getInstance().initMonthMap();
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                applicationBasketButton.setText(Double.toString(IMatDataHandler.getInstance().getShoppingCart().getTotal()) + " kr");
            }
        });
        searchArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    switchPage(MainViewController.getPage());
                }
            }
        });





        searchArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("") || newValue.isEmpty() || newValue.isBlank()){
                List<Product> result = IMatDataHandler.getInstance().getProducts();
                MainViewController.getInstance().setProductList(result);
                MainViewController.getInstance().populateMainView();
            }
            else {
                List<Product> result = IMatDataHandler.getInstance().findProducts(newValue);
                MainViewController.getInstance().setProductList(result);
                MainViewController.getInstance().populateMainView();
            }
        });

        // Button Actions
        closeLoginPopupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginPopup.toBack();
            }
        });
        loginCreateAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loginPopup.toBack();
                MainViewController.getInstance().hideMainViewBasket();
                switchPage(CreateAccountViewController2.getPage());
            }
        });
        accountButtonLoggedIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginPopup.toBack();
                MainViewController.getInstance().hideMainViewBasket();
                switchPage(AccountViewController.getPage());
            }
        });
        loginMainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(loginTelephoneTextField.getText().isEmpty() || loginTelephoneTextField.getText().isBlank() || !AccountViewController.isNumeric(loginTelephoneTextField.getText())){
                    loginError.toFront();
                    loginError.setText("Fel telefonnummer eller lösenord!");
                    loginTelephoneTextField.setStyle("-fx-border-width: 10");
                    loginTelephoneTextField.setStyle("-fx-border-color: red");
                    loginPasswordTextField.setStyle("-fx-border-width: 10");
                    loginPasswordTextField.setStyle("-fx-border-color: red");
                }
                else {
                    loginError.toBack();
                    loginPasswordTextField.setStyle("-fx-border-width: 0");
                    loginTelephoneTextField.setStyle("-fx-border-width: 0");
                    login();
                    loginPopup.toBack();
                    MainViewController.getInstance().hideMainViewBasket();
                }
            }
        });


        applicationAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginPopup.toFront();
            }
        });

        applicationBasketButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainViewController.getInstance().showMainViewBasket();
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switchPage(MainViewController.getPage());
            }
        });
        applicationHelpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {applicationHelpPopUp.toFront();}

        });
        applicationHelpPopUpCloseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {applicationHelpPopUp.toBack();}

        });

    }
    public boolean isLogged_in(){
        return logged_in;
    }
    public void login() {
            logged_in = true;
            accountButtonLoggedIn.setText(IMatDataHandler.getInstance().getCustomer().getFirstName());
            accountButtonLoggedIn.toFront();
    }
    public void logout(){
        logged_in = false;
        applicationAccountButton.toFront();
    }



    ColorAdjust enterAdjust = new ColorAdjust(0, 0, -0.1, 0);
    ColorAdjust exitAdjust = new ColorAdjust(0, 0, 0, 0);
    ColorAdjust pressAdjust = new ColorAdjust(0, 0, -0.2, 0);

    public void Search(){
        switchPage(MainViewController.getPage());
    }

    // -- Specific Attributes -- //
    @FXML
    private TextField searchArea;
    @FXML
    private Button accountButtonLoggedIn;
    // Main View

    @FXML
    private AnchorPane mainViewRoot;
    @FXML
    private AnchorPane accountViewRoot;
    @FXML
    private AnchorPane pageRoot;
    @FXML
    private Button homeButton;
    @FXML
    private Label loginError;

    @FXML
    private AnchorPane applicationRoot;
    @FXML
    public Parent parent;
    @FXML
    private Label pathLabel;
    @FXML
    private Button testButton;
    @FXML
    private Button testButton2;

    // Navigation Bar Component

    @FXML
    private Button applicationAccountButton;
    @FXML
    private Button applicationBasketButton;
    @FXML
    private Button applicationHelpButton;

    // Main View

    @FXML
    public AnchorPane mainViewAnchorPane;

    // Account View
    @FXML
    public AnchorPane accountViewAnchorPane;


    // Basket View
    @FXML
    private AnchorPane basketViewAnchorPane;

    // Create Account View
    @FXML
    private Button createAccountMainButton;
    @FXML
    private Button createAccountBackButton;
    @FXML
    private Button createAccountShopButton;

    // Login View
    @FXML
    private AnchorPane loginPopup;
    @FXML
    private Button loginMainButton;
    @FXML
    private Button loginCreateAccountButton;
    @FXML
    private Button closeLoginPopupButton;
    @FXML
    private Button loginBackButton;
    @FXML
    private Button loginShopButton;
    @FXML
    private TextField loginTelephoneTextField;
    @FXML
    private TextField loginPasswordTextField;
    @FXML
    private AnchorPane mainViewItemDetails;

    // Confirmation View

    @FXML
    private Button confirmationMainButton;

    // Popups
    @FXML
    private AnchorPane applicationHelpPopUp;
    @FXML
    private Button applicationHelpPopUpCloseButton;


    // -- Methods -- //
    public void addShoppingList(ArrayList<ShoppingItem> newList, String name){
        Integer var = shoppingLists.size()-1;
        shoppingListNames.put(var, name);
        shoppingLists.add(newList);
    }
    public ArrayList<ArrayList<ShoppingItem>> getShoppingLists(){
        return shoppingLists;
    }
    public HashMap<Integer, String> getShoppingListNames(){
        return shoppingListNames;
    }

    public void switchPage(Pane page) {
        if (pageRoot.getChildren().size() > 0) {
            pageRoot.getChildren().clear();
            System.out.println("SWITCH ASS");
        }

        pageRoot.getChildren().add(page);
    }

    // Main View Methods

    // Account Methods


    // Create Account Methods


    // General Methods
}