package imat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class customTitledPane2 extends TitledPane {
    @FXML
    private TitledPane thePane;
    @FXML
    public Text paneCategoryText;
    @FXML
    public FlowPane theFlowPane;
    public customTitledPane2(String first) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("customTitledPane2.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        paneCategoryText.setText(first);



    }

}

