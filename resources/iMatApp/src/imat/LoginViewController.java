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
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class LoginViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static LoginViewController instance = null;

    // -- Methods -- //

    private static synchronized LoginViewController getInstance() {
        if (instance == null) {
            instance = new LoginViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private LoginViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("login_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Button Actions

        loginViewToMainViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });

        loginViewBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(BasketViewController.getPage());
            }
        });

        loginViewLoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!AccountViewController.isNumeric(loginTelephoneTextField.getText())){
                    loginError.setText("Telefonnummret måste bestå av siffror!");
                    loginTelephoneTextField.setStyle("-fx-border-width: 10");
                    loginTelephoneTextField.setStyle("-fx-border-color: red");
                    loginPasswordTextField.setStyle("-fx-border-width: 10");
                    loginPasswordTextField.setStyle("-fx-border-color: red");
                }
                else if (loginTelephoneTextField.getText().isEmpty() || loginTelephoneTextField.getText().isBlank() || IMatDataHandler.getInstance().getCustomer().getMobilePhoneNumber().equals(loginTelephoneTextField.getText()) ||IMatDataHandler.getInstance().getUser().getPassword().equals(loginPasswordTextField.getText())) {
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
                    ApplicationController.getInstance().login();
                    MainViewController.getInstance().hideMainViewBasket();
                }
            }
        });

        loginViewCreateAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(CreateAccountViewController.getPage());
            }
        });

    }

    // -- FXML Objects -- //
    @FXML
    private Label loginError;
    @FXML
    private TextField loginTelephoneTextField;
    @FXML
    private TextField loginPasswordTextField;

    @FXML
    private Button loginViewToMainViewButton;
    @FXML
    private Button loginViewBackButton;
    @FXML
    private Button loginViewLoginButton;
    @FXML
    private Button loginViewCreateAccountButton;
}
