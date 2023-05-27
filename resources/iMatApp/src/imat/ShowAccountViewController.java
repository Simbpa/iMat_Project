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
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;

import java.util.ArrayList;
import java.util.List;

public class ShowAccountViewController extends AnchorPane {


    private ArrayList<String> orderInformation = new ArrayList<String>();
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

        adressButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(adressButton.getText().equals("Ändra")){
                    adressButton.setText("Spara");
                    loginAdressTextField.setEditable(true);
                }
                else{
                    adressButton.setText("Ändra");
                    loginAdressTextField.setEditable(false);
                }

            }
        });
        postcodeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(postcodeButton.getText().equals("Ändra")){
                    postcodeButton.setText("Spara");
                    loginPostCodeTextField.setEditable(true);
                }
                else{
                    postcodeButton.setText("Ändra");
                    loginPostCodeTextField.setEditable(false);
                }

            }
        });
        cityButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(cityButton.getText().equals("Ändra")){
                    cityButton.setText("Spara");
                    loginCityTextField1.setEditable(true);
                }
                else{
                    cityButton.setText("Ändra");
                    loginCityTextField1.setEditable(false);
                }

            }
        });

        emailButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(emailButton.getText().equals("Ändra")){
                    System.out.print("funkar");
                    emailButton.setText("Spara");
                    loginEmailTextField.setEditable(true);
                }
                else{
                    System.out.print("inte");

                    emailButton.setText("Ändra");
                    loginEmailTextField.setEditable(false);
                }
            }
        });

        phoneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(phoneButton.getText().equals("Ändra")){
                    phoneButton.setText("Spara");
                    loginTelephoneTextField.setEditable(true);
                }
                else{
                    phoneButton.setText("Ändra");
                    loginTelephoneTextField.setEditable(false);
                }

            }
        });


        showAccountViewBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(BasketViewController.getPage());
            }
        });
        showAccountViewNextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                orderInformation.add(loginAdressTextField.getText());
                orderInformation.add(loginCityTextField1.getText());
                orderInformation.add(loginPostCodeTextField.getText());
                orderInformation.add(loginEmailTextField.getText());
                orderInformation.add(loginTelephoneTextField.getText());
                DeliveryViewController.getInstance().setDeliveryAdressField(orderInformation.get(0));
                DeliveryViewController.getInstance().setDeliveryPostCodeField(orderInformation.get(2));
                ApplicationController.getInstance().switchPage(DeliveryViewController.getPage());
            }
        });

        showAccountPageNameText.toFront();

    }

    // -- FXML Objects -- //
    @FXML
    private TextField searchArea;
    @FXML
    private Button adressButton;
    @FXML
    private Button phoneButton;
    @FXML
    private Button postcodeButton;
    @FXML
    private Button cityButton;
    @FXML
    private Button emailButton;
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
