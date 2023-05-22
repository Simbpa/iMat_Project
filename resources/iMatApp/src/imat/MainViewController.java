// This module serves as the main controller of the program

// -- Packages -- //
package imat;


// -- Imports -- //
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;

public class MainViewController implements Initializable {

    // -- General Attributes -- //
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public HashMap<Integer, String> monthMap = new HashMap<Integer, String>();
    public HashMap<String, accountListItem> orderMap = new HashMap<String, accountListItem>();
    private Map<String, MainViewItem> mainViewItemMap = new HashMap<String, MainViewItem>();

    List<Product> productList =  iMatDataHandler.getProducts();

    ColorAdjust enterAdjust = new ColorAdjust(0, 0, -0.1, 0);
    ColorAdjust exitAdjust = new ColorAdjust(0,0,0, 0);
    ColorAdjust pressAdjust = new ColorAdjust(0,0,-0.2, 0);




    // -- Specific Attributes -- //

    @FXML
    private Label pathLabel;

    // Navigation Bar Component
    @FXML
    private Text navigationBarIMatButton;
    @FXML
    private TextField navigationBarSearchBar;
    @FXML
    private Button navigationBarHelpButton;
    @FXML
    private Button navigationBarAccountButton;
    @FXML
    private Button navigationBarBasketButton;

    // Main View
    @FXML
    private FlowPane mainViewFlowPane;

    // Account View
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

    // Confirmation View

    @FXML
    private Button confirmationMainButton;


    // -- Methods -- //
    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();
        mainViewInitialize();

        //pathLabel.setText(iMatDirectory);
    }

    // Navigation Bar Methods

    public void navigationBarHelpButton() throws IOException {
        // Pop Up
    }
    public void navigationBarBasketButton() throws IOException {
        // Pop Up, if not greyed out
    }

    // Main View Methods

    public void mainViewInitialize() {
        populateItemMap();
        populateMainView();
    }
    public void populateItemMap() {
        for (Product product: productList) {
            MainViewItem mainViewItem = new MainViewItem(product, this);
            mainViewItemMap.put(product.getName(), mainViewItem);
        }
    }
    public void populateMainView() {
        mainViewFlowPane.getChildren().clear();
            for (Product product : productList) {
                mainViewFlowPane.getChildren().add(mainViewItemMap.get(product.getName()));
            }
    }

    // Account Methods
    public void myAccountButtonClick() {
        myAccountWindow.toFront();
    }
    public void myListButtonClick() {
        myListWindow.toFront();
    }
    public void myHistoryButtonClick() {
        myHistoryWindow.toFront();
    }
    public void myAccountButtonEnter() {
        myAccountButton.setEffect(enterAdjust);
    }
    public void myAccountButtonExit() {
        myAccountButton.setEffect(exitAdjust);
    }
    public void myAccountButtonPress() {
        myAccountButton.setEffect(pressAdjust);
    }

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

    public void initAccountView(){
        initMonthMap();
        ArrayList<ArrayList<Order>> groupedOrders = new ArrayList<ArrayList<Order>>();
        for (Order order : IMatDataHandler.getInstance().getOrders()){
            if(groupedOrders.size() != 0){
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

    // Confirmation View Methods


    // General Methods

    public void toMainView() throws IOException{

    }
    public void toBasketView() {

    }
    public void toLoginView() {

    }
    public void toShowAccountView() {

    }
    public void toCreateAccountView() {

    }
    public void toDeliveryView() {

    }
    public void toPaymentView() {

    }
    public void toConfirmationView() {

    }
    public void toAccountView() {

    }


    public void changeView(String filename, Control fxml_object) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filename));
        Stage stage = (Stage) fxml_object.getScene().getWindow();
        stage.setScene(new Scene(root, 1280, 800));
    }


}