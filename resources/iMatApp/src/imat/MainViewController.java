
package imat;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class MainViewController implements Initializable {

    @FXML
    private Label pathLabel;

    // Account View
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

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    public void initialize(URL url, ResourceBundle rb) {

        String iMatDirectory = iMatDataHandler.imatDirectory();

        //pathLabel.setText(iMatDirectory);
    }
    // Specific Methods
    public void myAccountButtonClick() throws IOException {
        myAccountWindow.toFront();
    }
    public void myListButtonClick() throws IOException {
        myListWindow.toFront();
    }
    public void myHistoryButtonClick() throws IOException {
        myHistoryWindow.toFront();
    }



    // General Methods
    private void changeView(String filename, Control fxml_object) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filename));
        Stage stage = (Stage) fxml_object.getScene().getWindow();
        stage.setScene(new Scene(root, 1280, 800));
    }

}