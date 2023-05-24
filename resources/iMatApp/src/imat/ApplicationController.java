// This module serves as the main controller of the program

// -- Packages -- //
package imat;


// -- Imports -- //

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import se.chalmers.cse.dat216.project.IMatDataHandler;


public class ApplicationController extends AnchorPane {

    // -- General Attributes -- //
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public ApplicationController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("imat_app.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        switchPage(MainViewController.getPage());

        applicationAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switchPage(AccountViewController.getPage());
            }
        });


    }


    ColorAdjust enterAdjust = new ColorAdjust(0, 0, -0.1, 0);
    ColorAdjust exitAdjust = new ColorAdjust(0, 0, 0, 0);
    ColorAdjust pressAdjust = new ColorAdjust(0, 0, -0.2, 0);


    // -- Specific Attributes -- //

    // Main View
    @FXML
    private AnchorPane mainViewRoot;
    @FXML
    private AnchorPane accountViewRoot;
    @FXML
    private AnchorPane pageRoot;

    @FXML
    private AnchorPane applicationRoot;
    @FXML
    public Parent parent;
    @FXML
    private Label pathLabel;
    @FXML
    private Button testButton;
    @FXML
    private Button testButton2;

    // Navigation Bar Component
    @FXML
    private Button applicationAccountButton;
    // Main View

    @FXML
    public AnchorPane mainViewAnchorPane;

    // Account View
    @FXML
    public AnchorPane accountViewAnchorPane;


    // Basket View
    @FXML
    private AnchorPane basketViewAnchorPane;

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


    // -- Methods -- //

    public void switchPage(Pane page) {
        if (pageRoot.getChildren().size() > 0) {
            pageRoot.getChildren().clear();
            System.out.println("SWITCH ASS");
        }

        pageRoot.getChildren().add(page);
    }

    // Account Methods



    // Create Account Methods


    // General Methods
}