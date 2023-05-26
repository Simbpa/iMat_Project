package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class ShowAccountViewController extends AnchorPane {



    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static ShowAccountViewController instance = null;

    // -- Methods -- //

    private static synchronized ShowAccountViewController getInstance() {
        if (instance == null) {
            instance = new ShowAccountViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private ShowAccountViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("show_account_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Button Actions
        showAccountViewtoMainViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });
        showAccountPageNameText.setText(IMatDataHandler.getInstance().getCustomer().getFirstName() + " " +IMatDataHandler.getInstance().getCustomer().getLastName());
        loginEmailTextField.setText(IMatDataHandler.getInstance().getCustomer().getEmail());
        loginAdressTextField.setText(IMatDataHandler.getInstance().getCustomer().getAddress());
        loginPostCodeTextField.setText(IMatDataHandler.getInstance().getCustomer().getPostCode());
        loginTelephoneTextField.setText(IMatDataHandler.getInstance().getCustomer().getMobilePhoneNumber());
        loginCityTextField1.setText(IMatDataHandler.getInstance().getCustomer().getPostAddress());


        showAccountViewBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(BasketViewController.getPage());
            }
        });
        showAccountViewNextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(DeliveryViewController.getPage());
            }
        });

        showAccountPageNameText.toFront();

    }

    // -- FXML Objects -- //
    @FXML
    private Button showAccountViewtoMainViewButton;
    @FXML
    private Button showAccountViewBackButton;
    @FXML
    private Button showAccountViewNextButton;
    @FXML
    private TextField showAccountPageNameText;
    @FXML
    private TextField loginEmailTextField;
    @FXML
    private TextField loginAdressTextField;
    @FXML
    private TextField loginCityTextField1;
    @FXML
    private TextField loginPostCodeTextField;
    @FXML
    private TextField loginTelephoneTextField;
}
