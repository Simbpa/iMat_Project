package imat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.util.*;

public class DeliveryViewController extends AnchorPane {
    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    private ArrayList<DeliveryDateItem> deliveryDateItemList = new ArrayList<>();
    private static DeliveryViewController instance = null;

    // -- Methods -- //

    public static synchronized DeliveryViewController getInstance() {
        if (instance == null) {
            instance = new DeliveryViewController();
        }
        return instance;
    }

    public static synchronized Pane getPage() {
        return getInstance();
    }

    private DeliveryViewController() {
        FXMLLoader loader = new FXMLLoader(ApplicationController.class.getResource("delivery_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        populateDeliveryViewList();
        populateDeliveryView();

        // Button Actions
        deliveryViewToMainViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(MainViewController.getPage());
            }
        });
        deliveryViewBackButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(ShowAccountViewController.getPage());
            }
        });
        deliveryViewNextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationController.getInstance().switchPage(PaymentViewController.getPage());
            }
        });
        deliveryViewLeftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deliveryViewScrollPane.setHvalue(0.01);

            }
        });
        deliveryViewRightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deliveryViewScrollPane.setHvalue(0.98);
            }
        });

    }

    public void setDeliveryAdressField(String s) {
        this.deliveryAdressField.setText(s);
    }
    public void setDeliveryPostCodeField(String s) {
        this.deliveryPostcodeField.setText(s);
    }

    // -- FXML-Object -- //
    @FXML
    private TextField deliveryPostcodeField;
    @FXML
    private TextField deliveryAdressField;
    @FXML
    private Button deliveryViewToMainViewButton;
    @FXML
    private Button deliveryViewBackButton;
    @FXML
    private Button deliveryViewNextButton;
    @FXML
    private FlowPane deliveryViewFlowPane;
    @FXML
    private Button deliveryViewLeftButton;
    @FXML
    private Button deliveryViewRightButton;
    @FXML
    private ScrollPane deliveryViewScrollPane;

    // -- Methods -- //

    public void populateDeliveryViewList() {

        for (int i = 0; i < 6; i++) {
            Calendar calendar = Calendar.getInstance((TimeZone.getTimeZone("GMT")));
            calendar.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
            calendar.add(Calendar.DATE, i);

            String weekDay = "Someday";
            String day = "0";
            String month = "Somemonth";

            // Determine week day
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    weekDay = "Måndag";     break;
                case 2:
                    weekDay = "Tisdag";    break;
                case 3:
                    weekDay = "Onsdag";  break;
                case 4:
                    weekDay = "Torsdag";   break;
                case 5:
                    weekDay = "Fredag";     break;
                case 6:
                    weekDay = "Lördag";   break;
                case 7:
                    weekDay = "Söndag";     break;
            }
            // Determine day
            day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));

            // Determine month
            int month_number = calendar.get(Calendar.MONTH);
            switch (month_number) {
                case 0:  month = "Januari";   break;
                case 1:  month = "Febuari";   break;
                case 2:  month = "Mars";      break;
                case 3:  month = "April";     break;
                case 4:  month = "Maj";       break;
                case 5:  month = "Juni";      break;
                case 6:  month = "Juli";      break;
                case 7:  month = "Augusti";   break;
                case 8:  month = "September"; break;
                case 9:  month = "Oktober";   break;
                case 10: month = "November";  break;
                case 11: month = "December";  break;
            }

            // Create item
            deliveryDateItemList.add(new DeliveryDateItem(weekDay, day, month));

        }
    }

    public void populateDeliveryView() {
        deliveryViewFlowPane.getChildren().clear();
        for (int i = 0; i < deliveryDateItemList.size(); i++) {
            deliveryViewFlowPane.getChildren().add(deliveryDateItemList.get(i));
        }
    }

    public void resetDateItemColours() {
        for (DeliveryDateItem deliveryDateItem: deliveryDateItemList) {
            deliveryDateItem.colour = "Green";
            deliveryDateItem.morningButton.setStyle("-fx-background-color: #C9E8B7");
            deliveryDateItem.eveningButton.setStyle("-fx-background-color: #C9E8B7");
            deliveryDateItem.nightButton.setStyle("-fx-background-color: #C9E8B7");
        }
    }


}
