// This module serves as the controller for the account view

// -- Packages -- //
package imat;

// -- Imports -- //
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
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

    private static synchronized AccountViewController getInstance() {
        if (instance == null) {
            instance = new AccountViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private AccountViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("account_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Button Actions

        myAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showMyAccountWindow();
            }
        });

        myListButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showMyListWindow();
            }
        });

        myHistoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showMyHistoryWindow();
            }
        });

    }

    // -- FXML Object -- //

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




    // -- Methods -- //

    public void showMyAccountWindow() {
        myAccountWindow.toFront();
    }
    public void showMyListWindow() {
        myListWindow.toFront();
        initListView();
    }
    public void showMyHistoryWindow() {
        myHistoryWindow.toFront();
        initHistoryView();
    }

    // -- Extra Code -- //

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
        customTitledPane newPane = new customTitledPane();
        String dateText = monthMap.get(group.get(0).getDate().getMonth()) + " " + (group.get(0).getDate().getYear() + 1900) + "                                                        ";
        newPane.setText(dateText + String.valueOf(group.size()) + " köp");
        FlowPane fp = new FlowPane();
        for (Order order : group){
            accountHistoryListItem orderItem = new accountHistoryListItem(order, monthMap);
            fp.getChildren().add(orderItem);
        }
        newPane.setContent(fp);
        accountHistoryAccordion.getPanes().add(newPane);
    }
    public void initHistoryView(){
        ArrayList<ShoppingItem> test = new ArrayList<ShoppingItem>();
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
        IMatDataHandler.getInstance().shutDown();

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
        ArrayList<ShoppingItem> test = new ArrayList<ShoppingItem>();
        Random rand = new Random();
        for(int i = 1; i<10; i++){
            ShoppingItem test2 = new ShoppingItem(IMatDataHandler.getInstance().getProduct(i), rand.nextDouble(11));
            test.add(test2);
        }
        accountListListItem test3 = new accountListListItem("Söndag", test);
        accountListFlowPane.getChildren().add(test3);

        test = new ArrayList<ShoppingItem>();
        rand = new Random();
        for(int i = 11; i<35; i++){
            ShoppingItem test2 = new ShoppingItem(IMatDataHandler.getInstance().getProduct(i), rand.nextDouble(11));
            test.add(test2);
        }
        test3 = new accountListListItem("Storhandling", test);
        accountListFlowPane.getChildren().add(test3);
    }

}
