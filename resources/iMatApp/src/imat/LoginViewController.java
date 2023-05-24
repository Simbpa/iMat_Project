package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
                //ApplicationController.getInstance().switchPage();
            }
        });

        loginViewCreateAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //ApplicationController.getInstance().switchPage();
            }
        });

    }

    // -- FXML Objects -- //

    @FXML
    private Button loginViewToMainViewButton;
    @FXML
    private Button loginViewBackButton;
    @FXML
    private Button loginViewLoginButton;
    @FXML
    private Button loginViewCreateAccountButton;
}
