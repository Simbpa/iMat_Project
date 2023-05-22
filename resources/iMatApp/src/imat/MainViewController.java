
package imat;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

public class MainViewController implements Initializable {
    public HashMap<Integer, String> monthMap = new HashMap<Integer, String>();
    public HashMap<String, accountListItem> orderMap = new HashMap<String, accountListItem>();


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


    // Account View
    @FXML
    private Accordion accountHistoryAccordion;
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
    private Text paneDateText;
    @FXML
    private FlowPane theFlowPane;


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



    ColorAdjust enterAdjust = new ColorAdjust(0, 0, -0.1, 0);
    ColorAdjust exitAdjust = new ColorAdjust(0,0,0, 0);
    ColorAdjust pressAdjust = new ColorAdjust(0,0,-0.2, 0);

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();

        //pathLabel.setText(iMatDirectory);
    }

    // Navigation Bar Methods

    public void popUpHelp() {
        // Pop Up
    }
    public void popUpAccount() throws IOException {
        changeView("imat_app.fxml", navigationBarHelpButton);
    }
    public void popUpBasket() {
        // Pop Up, if not greyed out
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
        initHistoryView();
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

    public void toShopView() {

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

    /*private void createTitledPane(ArrayList<Order> group){
        customTitledPane newPane = new customTitledPane(group, this, monthMap);
        String dateText = monthMap.get(group.get(0).getDate().getMonth()) + "" + group.get(0).getDate().getYear() + "              ";
        newPane.paneDateText.setText(dateText);
        newPane.setText(String.valueOf(group.size()));

        for (Order order : group){
            accountListItem orderItem = new accountListItem(order, newPane, monthMap);
            newPane.theFlowPane.getChildren().add(orderItem);
        }
        accountHistoryAccordion.getPanes().add(newPane);
    }*/

    private void createTitledPane(ArrayList<Order> group){
        TitledPane newPane = new TitledPane();
        String dateText = monthMap.get(group.get(0).getDate().getMonth()) + " " + (group.get(0).getDate().getYear() + 1900) + "                                        ";
        newPane.setText(dateText + String.valueOf(group.size()) + " Varor");
        FlowPane fp = new FlowPane();
        for (Order order : group){
            accountListItem orderItem = new accountListItem(order, newPane, monthMap);
            fp.getChildren().add(orderItem);
        }
        newPane.setContent(fp);
        accountHistoryAccordion.getPanes().add(newPane);
    }
    public void initHistoryView(){
        initMonthMap();
        ArrayList<ArrayList<Order>> groupedOrders = new ArrayList<ArrayList<Order>>();

        for (Order order : IMatDataHandler.getInstance().getOrders()){
                groupedOrders = findAndAdd(groupedOrders, order);
        }
        System.out.println(groupedOrders);

        for (ArrayList<Order> group : groupedOrders){
            createTitledPane(group);
        }
    }




    public void changeView(String filename, Control fxml_object) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filename));
        Stage stage = (Stage) fxml_object.getScene().getWindow();
        stage.setScene(new Scene(root, 1280, 800));
    }


}