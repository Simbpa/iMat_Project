package imat;

// -- Imports -- //
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.List;

public class DeliveryDateItem extends AnchorPane {
    String weekDay;
    String day;
    String month;

    // -- Constructor -- //
    public DeliveryDateItem(String givenWeekDay, String givenDay, String givenMonth) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("delivery_date_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        weekDay = givenWeekDay;
        day = givenDay;
        month = givenMonth;

        dayLabel.setText(weekDay);
        dateLabel.setText(day + " " + month);


        // Button Actions
        /*
        minusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //removeItemFromShoppingCart();
            }
        });
         */
    }

    // -- FXMl-Objekt -- //

    @FXML
    private Label dayLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Button morningButton;
    @FXML
    private Button eveningButton;
    @FXML
    private Button nightButton;

    // -- Methods -- //

}


