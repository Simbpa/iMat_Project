package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class CreateAccountViewController2 extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static CreateAccountViewController2 instance = null;

    // -- Methods -- //

    private static synchronized CreateAccountViewController2 getInstance() {
        if (instance == null) {
            instance = new CreateAccountViewController2();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private CreateAccountViewController2() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("create_account_view2.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Button Actions
        createAccountViewBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });
        createAccountViewCreateAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveInformation();
                ApplicationController.getInstance().login();
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });

    }

    public void saveInformation(){
        Customer customer = IMatDataHandler.getInstance().getCustomer();
        customer.setAddress(adressField.getText());
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setEmail(emailField.getText());
        customer.setPostCode(postcodeField.getText());
        customer.setMobilePhoneNumber(phoneField.getText());
        customer.setPostAddress(cityField.getText());
        AccountViewController.getInstance().initAccountView();
        IMatDataHandler.getInstance().shutDown();
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
