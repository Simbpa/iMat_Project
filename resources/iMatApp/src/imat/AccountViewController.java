// This module serves as the controller for the account view

// -- Packages -- //
package imat;

// -- Imports -- //
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import javax.swing.*;

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


    // -- Methods -- //

    public void showMyAccountWindow() {
        myAccountWindow.toFront();
    }
    public void showMyListWindow() {
        myListWindow.toFront();
    }
    public void showMyHistoryWindow() {
        myHistoryWindow.toFront();
    }

}
