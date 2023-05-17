
package imat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class MainViewController implements Initializable {

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



    ColorAdjust hoverAdjust = new ColorAdjust(0, 0, -0.4, 0);

    ColorAdjust pressAdjust = new ColorAdjust(0,0,-0.2, 0);

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();

        //pathLabel.setText(iMatDirectory);
    }

    // Navigation Bar Methods

    public void navigationBarHelpButton() throws IOException {
        // Pop Up
    }
    public void navigationBarBasketButton() throws IOException {
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






    public void changeView(String filename, Control fxml_object) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filename));
        Stage stage = (Stage) fxml_object.getScene().getWindow();
        stage.setScene(new Scene(root, 1280, 800));
    }
    public void buttonHover(Button button) {
        button.setEffect(hoverAdjust);
    }
    public void buttonPress(Button button) {
        button.setEffect(pressAdjust);
    }


}