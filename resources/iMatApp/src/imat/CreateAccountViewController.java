package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.w3c.dom.Text;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class CreateAccountViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static CreateAccountViewController instance = null;

    // -- Methods -- //

    private static synchronized CreateAccountViewController getInstance() {
        if (instance == null) {
            instance = new CreateAccountViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private CreateAccountViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("create_account_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Button Actions
        createAccountViewToMainViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });
        createAccountViewBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(BasketViewController.getPage());
            }
        });
        createAccountViewCreateAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveInformation();
                ApplicationController.getInstance().switchPage(ShowAccountViewController.getPage());
            }
        });

    }

    public void saveInformation(){
        System.out.println("here");
        Customer customer = IMatDataHandler.getInstance().getCustomer();
        customer.setAddress(adressField.getText());
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setEmail(emailField.getText());
        customer.setPostCode(postcodeField.getText());
        customer.setMobilePhoneNumber(phoneField.getText());
        customer.setPostAddress(cityField.getText());
    }

    // -- FXML Objects -- //
    @FXML
    private TextField cityField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField adressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField postcodeField;


    @FXML
    private Button createAccountViewToMainViewButton;
    @FXML
    private Button createAccountViewBackButton;
    @FXML
    private Button createAccountViewCreateAccountButton;
}
