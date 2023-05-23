package imat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
public class customTitledPane extends TitledPane {
    private MainViewController parentController;
    private ArrayList<Order> group;
    @FXML
    private TitledPane thePane;
    @FXML
    public Text paneDateText;
    @FXML
    public FlowPane theFlowPane;
    public customTitledPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("customTitledPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }




    }

}

