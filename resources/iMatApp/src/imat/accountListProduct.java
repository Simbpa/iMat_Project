package imat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class accountListProduct extends AnchorPane {

    @FXML
    private ImageView accountHistoryProductImage;
    @FXML
    public Text accountHistoryProductName;
    @FXML
    public Text accountHistoryProductAmount;

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

        this.accountHistoryProductAmount.setText(String.valueOf(product.getAmount()) + " st");
        this.accountHistoryProductName.setText(product.getProduct().getName());

        this.accountHistoryProductImage.setImage(IMatDataHandler.getInstance().getFXImage(product.getProduct()));
        this.parentController = mainController;

    }

}
