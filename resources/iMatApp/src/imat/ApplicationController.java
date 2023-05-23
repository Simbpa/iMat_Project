// This module serves as the main controller of the program

// -- Packages -- //
package imat;


// -- Imports -- //
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;



public class ApplicationController extends AnchorPane {

    // -- General Attributes -- //
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public ApplicationController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("imat_app.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        switchPage(MainViewController.getPage());

        applicationAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switchPage(AccountViewController.getPage());
            }
        });


    }

    public HashMap<Integer, String> monthMap = new HashMap<Integer, String>();
    public HashMap<String, accountListItem> orderMap = new HashMap<String, accountListItem>();


    ColorAdjust enterAdjust = new ColorAdjust(0, 0, -0.1, 0);
    ColorAdjust exitAdjust = new ColorAdjust(0, 0, 0, 0);
    ColorAdjust pressAdjust = new ColorAdjust(0, 0, -0.2, 0);


    // -- Specific Attributes -- //

    // Main View
    @FXML
    private AnchorPane mainViewRoot;
    @FXML
    private AnchorPane accountViewRoot;
    @FXML
    private AnchorPane pageRoot;

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
    private Button loginMainButton;
    @FXML
    private Button loginCreateAccountButton;
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


    // -- Methods -- //

    public void switchPage(Pane page) {
        if (pageRoot.getChildren().size() > 0) {
            pageRoot.getChildren().clear();
            System.out.println("SWITCH ASS");
        }

        pageRoot.getChildren().add(page);
    }

    // Account Methods


    public void initMonthMap() {
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
        for (ArrayList<Order> group : groupedOrders) {
            if (group.get(0).getDate().getYear() == newOrder.getDate().getYear() && group.get(0).getDate().getMonth() == newOrder.getDate().getMonth()) {
                group.add(newOrder);
                return groupedOrders;
            }
        }
        ArrayList<Order> newGroup = new ArrayList<Order>();
        newGroup.add(newOrder);
        groupedOrders.add(newGroup);
        return groupedOrders;
    }

    public void initAccountView() {
        initMonthMap();
        ArrayList<ArrayList<Order>> groupedOrders = new ArrayList<ArrayList<Order>>();
        for (Order order : IMatDataHandler.getInstance().getOrders()) {
            if (groupedOrders.size() != 0) {
                groupedOrders = findAndAdd(groupedOrders, order);
                accountListItem AccountListItem = new accountListItem(order, this, monthMap);
                orderMap.put(String.valueOf(order.getOrderNumber()), AccountListItem);
            }
        }

    }


    // Create Account Methods

    public void CreateAccountMainButtonClick() {
        // Save information
        // Change view to ShowAccountView

    }

    // Login View Methods

    public void loginMainButtonClick() {
        // If correct -> Show Account View
        // If not correct -> Error Message
    }

    // General Methods
}