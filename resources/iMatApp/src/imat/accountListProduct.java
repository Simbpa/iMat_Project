package imat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class accountListProduct extends AnchorPane {


    private TitledPane parentController;

    public accountListProduct(ShoppingItem product, TitledPane mainController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("account_list_product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = mainController;

    }

}
