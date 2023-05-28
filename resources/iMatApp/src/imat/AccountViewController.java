// This module serves as the controller for the account view

// -- Packages -- //
package imat;

// -- Imports -- //
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class AccountViewController extends AnchorPane {

    private static AccountViewController instance = null;
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public static synchronized AccountViewController getInstance() {
        if (instance == null) {
            instance = new AccountViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private AccountViewController() {
        initMonthMap();
        /*IMatDataHandler.getInstance().getCustomer().setFirstName("Bert");
        IMatDataHandler.getInstance().getCustomer().setLastName("Karlsson");
        IMatDataHandler.getInstance().getCustomer().setAddress("Äpplevägen 42");
        IMatDataHandler.getInstance().getCustomer().setPostAddress("Göteborg");
        IMatDataHandler.getInstance().getCustomer().setPostCode("12345");
        IMatDataHandler.getInstance().getCustomer().setEmail("bert.karlsson@bert.se");
        IMatDataHandler.getInstance().shutDown();*/


        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("account_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        accountIndicator.toFront();

        // Button Actions
        initHistoryView();
        initListView();
        initAccountView();

        myAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hideAllIndicators();
                showMyAccountWindow();
                accountIndicator.toFront();
            }
        });

        myListButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hideAllIndicators();
                showMyListWindow();
                listIndicator.toFront();
            }
        });

        myHistoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showMyHistoryWindow();
                hideAllIndicators();
                historyIndicator.toFront();
            }
        });

        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().logout();
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });

    }

    // -- FXML Object -- //
    @FXML
    private TextField accountViewCard;
    @FXML
    private TextField accountViewMonth;
    @FXML
    private TextField accountViewYear;
    @FXML
    private TextField accountViewCVC;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField postcodeField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private Button saveButton;
    @FXML
    private Button myAccountButton;
    @FXML
    private Button myListButton;
    @FXML
    private Button myHistoryButton;
    @FXML
    private AnchorPane myAccountWindow;
    @FXML
    private AnchorPane myListWindow;
    @FXML
    private AnchorPane myHistoryWindow;
    @FXML
    private FlowPane accountListFlowPane;
    @FXML
    private Accordion accountHistoryAccordion;
    @FXML
    private Text paneDateText;
    @FXML
    private FlowPane theFlowPane;

    @FXML
    private Text firstnameError;
    @FXML
    private Text lastnameError;
    @FXML
    private Text postcodeError;
    @FXML
    private Text phoneErrorField;
    @FXML
    private AnchorPane listIndicator;
    @FXML
    private AnchorPane historyIndicator;
    @FXML
    private AnchorPane accountIndicator;
    @FXML
    private Button logOutButton;

    // -- Methods -- //
    public void saveAccountInfo(){
        if(!firstnameField.getText().isBlank() || !firstnameField.getText().isEmpty()){
            IMatDataHandler.getInstance().getCustomer().setFirstName(firstnameField.getText());
            firstnameError.setText(" ");
            firstnameField.setStyle("-fx-border-width: 0");
        }
        else{
            firstnameError.setText("Du måste fylla i detta fältet!");
            firstnameField.setStyle("-fx-border-width: 2");
            firstnameField.setStyle("-fx-border-color: red");
        }

        if(!lastnameField.getText().isBlank() || !lastnameField.getText().isEmpty()){
            IMatDataHandler.getInstance().getCustomer().setLastName(lastnameField.getText());
            lastnameError.setText(" ");
            lastnameField.setStyle("-fx-border-width: 0");
        }
        else{
            lastnameError.setText("Du måste fylla i detta fältet!");
            lastnameField.setStyle("-fx-border-width: 2");
            lastnameField.setStyle("-fx-border-color: red");
        }


        if(addressField != null){
            IMatDataHandler.getInstance().getCustomer().setAddress(addressField.getText());
        };
        if(cityField != null){
            IMatDataHandler.getInstance().getCustomer().setPostAddress(cityField.getText());
        }
        if(postcodeField.getText().isBlank() || postcodeField.getText().isEmpty()){
            postcodeError.setText("Du måste fylla i detta fältet!");

            postcodeField.setStyle("-fx-border-width: 2");
            postcodeField.setStyle("-fx-border-color: red");
        } else if (!isNumeric(postcodeField.getText())) {
            postcodeError.setText("Postkoden får bara innehålla siffror!");
            postcodeField.setStyle("-fx-border-width: 2");
            postcodeField.setStyle("-fx-border-color: red");
        } else{
            IMatDataHandler.getInstance().getCustomer().setLastName(postcodeField.getText());
            postcodeError.setText(" ");
            postcodeField.setStyle("-fx-border-width: 0");
        }

        if(phoneField.getText().isBlank() || phoneField.getText().isEmpty()){
            phoneErrorField.setText("Du måste fylla i detta fältet!");
            phoneField.setStyle("-fx-border-width: 2");
            phoneField.setStyle("-fx-border-color: red");
        } else if (!isNumeric(phoneField.getText())) {
            phoneErrorField.setText("Postkoden får bara innehålla siffror!");
            phoneField.setStyle("-fx-border-width: 2");
            phoneField.setStyle("-fx-border-color: red");
        } else{
            IMatDataHandler.getInstance().getCustomer().setLastName(phoneField.getText());
            phoneErrorField.setText(" ");
            phoneField.setStyle("-fx-border-width: 0");
        }
        if(emailField != null){
            IMatDataHandler.getInstance().getCustomer().setEmail(emailField.getText());
        };
        if(accountViewCard.getText() != null) {
            IMatDataHandler.getInstance().getCreditCard().setCardNumber(accountViewCard.getText());
        }
        if(accountViewCVC.getText() != null) {
            IMatDataHandler.getInstance().getCreditCard().setVerificationCode(Integer.valueOf(accountViewCVC.getText()));
        }
        if(accountViewMonth.getText() != null) {
            IMatDataHandler.getInstance().getCreditCard().setValidMonth(Integer.valueOf(accountViewMonth.getText()));
        }
        if(accountViewYear.getText() != null) {
            IMatDataHandler.getInstance().getCreditCard().setValidYear(Integer.valueOf(accountViewYear.getText()));
        }

        IMatDataHandler.getInstance().shutDown();
        initAccountView();
    }
    public void showMyAccountWindow() {
        myAccountWindow.toFront();
        initAccountView();
    }
    public void showMyListWindow() {
        myListWindow.toFront();
    }
    public void showMyHistoryWindow() {
        myHistoryWindow.toFront();
    }

    // -- Extra Code -- //

    public void initAccountView(){
        System.out.println(IMatDataHandler.getInstance().getCustomer().getFirstName());
        if(IMatDataHandler.getInstance().getCustomer().getFirstName() != null){
            firstnameField.setText(IMatDataHandler.getInstance().getCustomer().getFirstName());
        };
        if(IMatDataHandler.getInstance().getCustomer().getLastName() != null){
            lastnameField.setText(IMatDataHandler.getInstance().getCustomer().getLastName());
        };
        if(IMatDataHandler.getInstance().getCustomer().getAddress() != null){
            addressField.setText(IMatDataHandler.getInstance().getCustomer().getAddress());
        };
        if(IMatDataHandler.getInstance().getCustomer().getPostAddress() != null){
            cityField.setText(IMatDataHandler.getInstance().getCustomer().getPostAddress());
        };
        if(IMatDataHandler.getInstance().getCustomer().getPostCode() != null){
            postcodeField.setText(IMatDataHandler.getInstance().getCustomer().getPostCode());
        };
        if(IMatDataHandler.getInstance().getCustomer().getMobilePhoneNumber() != null){
            phoneField.setText(IMatDataHandler.getInstance().getCustomer().getMobilePhoneNumber());
        };
        if(IMatDataHandler.getInstance().getCustomer().getEmail() != null){
            emailField.setText(IMatDataHandler.getInstance().getCustomer().getEmail());
        };
        if(IMatDataHandler.getInstance().getCreditCard().getCardNumber() != null){
            accountViewCard.setText(IMatDataHandler.getInstance().getCreditCard().getCardNumber());
        };

            accountViewCVC.setText(Integer.toString(IMatDataHandler.getInstance().getCreditCard().getVerificationCode()));
        

            accountViewYear.setText(Integer.toString(IMatDataHandler.getInstance().getCreditCard().getValidYear()));

            accountViewMonth.setText(Integer.toString(IMatDataHandler.getInstance().getCreditCard().getValidMonth()));

    }

    public HashMap<Integer, String> monthMap = new HashMap<Integer, String>();
    public HashMap<String, accountListItem> orderMap = new HashMap<String, accountListItem>();

    public void initMonthMap(){
        monthMap.put(0, "Januari");
        monthMap.put(1, "Februari");
        monthMap.put(2, "Mars");
        monthMap.put(3, "April");
        monthMap.put(4, "Maj");
        monthMap.put(5, "Juni");
        monthMap.put(6, "Juli");
        monthMap.put(7, "Augusti");
        monthMap.put(8, "September");
        monthMap.put(9, "Oktober");
        monthMap.put(10, "November");
        monthMap.put(11, "December");
    }

    private ArrayList<ArrayList<Order>> findAndAdd(ArrayList<ArrayList<Order>> groupedOrders, Order newOrder) {
        for(ArrayList<Order> group : groupedOrders){
            if(group.get(0).getDate().getYear() == newOrder.getDate().getYear() && group.get(0).getDate().getMonth() == newOrder.getDate().getMonth()){
                group.add(newOrder);
                return groupedOrders;
            }
        }
        ArrayList<Order> newGroup = new ArrayList<Order>();
        newGroup.add(newOrder);
        groupedOrders.add(newGroup);
        return groupedOrders;
    }


    private void createTitledPane(ArrayList<Order> group){
        customTitledPane newPane = new customTitledPane(group, monthMap);
        newPane.setText(String.valueOf(group.size()) + " köp");
        FlowPane fp = new FlowPane();
        fp.setStyle("-fx-background-color: D2D2D2");
        for (Order order : group){
            accountHistoryListItem orderItem = new accountHistoryListItem(order, monthMap);
            fp.getChildren().add(orderItem);
        }
        newPane.setContent(fp);
        accountHistoryAccordion.getPanes().add(newPane);
    }
    public void initHistoryView(){
        /*ArrayList<ShoppingItem> test = new ArrayList<ShoppingItem>();
        for(int i = 1; i<20; i++){
            ShoppingItem test2 = new ShoppingItem(IMatDataHandler.getInstance().getProducts().get(i));
            test.add(test2);
        }
        IMatDataHandler.getInstance().placeOrder();
        IMatDataHandler.getInstance().getOrders().get(0).setItems(test);
        Date test3 = IMatDataHandler.getInstance().getOrders().get(0).getDate();
        test3.setMonth(test3.getMonth() - 1);
        IMatDataHandler.getInstance().getOrders().get(0).setDate(test3);

        for(int i = 1; i<20; i++){
            ShoppingItem test2 = new ShoppingItem(IMatDataHandler.getInstance().getProducts().get(i));
            test.add(test2);
        }
        IMatDataHandler.getInstance().placeOrder();
        IMatDataHandler.getInstance().getOrders().get(1).setItems(test);
        test3 = IMatDataHandler.getInstance().getOrders().get(1).getDate();
        test3.setMonth(test3.getMonth() - 2);
        IMatDataHandler.getInstance().getOrders().get(1).setDate(test3);
        IMatDataHandler.getInstance().shutDown();*/

        ArrayList<ArrayList<Order>> groupedOrders = new ArrayList<ArrayList<Order>>();

        for (Order order : IMatDataHandler.getInstance().getOrders()){
            groupedOrders = findAndAdd(groupedOrders, order);
        }
        accountHistoryAccordion.getPanes().clear();
        for (ArrayList<Order> group : groupedOrders){
            createTitledPane(group);
        }
    }

    public void initListView(){
        accountListFlowPane.getChildren().clear();
        HashMap<Integer, String> names = ApplicationController.getInstance().getShoppingListNames();
        int i = 0;
        for(ArrayList<ShoppingItem> list : ApplicationController.getInstance().getShoppingLists()) {
            accountListListItem test3 = new accountListListItem(names.get(i), list);
            accountListFlowPane.getChildren().add(test3);
            i++;
        }
    }

    public static boolean isNumeric(String string) {
        int intValue;

        System.out.println(String.format("Parsing string: \"%s\"", string));

        if(string == null || string.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return false;
    }

    public void hideAllIndicators() {
        accountIndicator.toBack();
        listIndicator.toBack();
        historyIndicator.toBack();
    }

}
