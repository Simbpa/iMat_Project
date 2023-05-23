package imat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class accountListListProduct extends AnchorPane {

    @FXML
    private ImageView accountHistoryProductImage;
    @FXML
    public Text accountHistoryProductName;
    @FXML
    public Text accountHistoryProductAmount;

    private MainViewController parentController;

    public accountListListProduct(ShoppingItem product, MainViewController mainController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("accountHistoryProduct.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.accountHistoryProductAmount.setText(String.valueOf(Math.round(product.getAmount())) + " st");
        this.accountHistoryProductName.setText(product.getProduct().getName());

        this.accountHistoryProductImage.setImage(IMatDataHandler.getInstance().getFXImage(product.getProduct()));
        this.parentController = mainController;

    }

}
