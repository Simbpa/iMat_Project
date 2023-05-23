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

public class BasketViewController extends AnchorPane {

    private static BasketViewController instance = null;
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private static synchronized BasketViewController getInstance() {
        if (instance == null) {
            instance = new BasketViewController();
        }

        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private BasketViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("basket_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Button Actions


    }

    // -- FXML Object -- //


    // -- Methods -- //

}