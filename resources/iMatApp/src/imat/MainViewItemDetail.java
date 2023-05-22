package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class MainViewItemDetail extends AnchorPane {

    MainViewController parentController;
    Product product;
    int amount = 0;

    @FXML
    private Label itemDetailNameLabel;
    @FXML
    private Label itemDetailPriceLabel;
    @FXML
    private Label itemDetailAmountLabel;
    @FXML
    private AnchorPane itemDetailPlusButton;
    @FXML
    private AnchorPane itemDetailMinusButton;


    public MainViewItemDetail(Product product, MainViewController mainViewController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("productinfo.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentController = mainViewController;
        this.product = product;

        //itemDetailImageView.setImage(parentController.iMatDataHandler.getFXImage(product));
        itemDetailNameLabel.setText(product.getName());
        itemDetailPriceLabel.setText(Double.toString(product.getPrice()));
        itemDetailAmountLabel.setText(Integer.toString(amount));

    }

    // -- Methods -- //

    public void increaseAmount() {
        amount += 1;
        itemDetailAmountLabel.setText(Integer.toString(amount));
    }

    public void decreaseAmount() {
        if(amount>=1){
            amount -= 1;
            itemDetailAmountLabel.setText(Integer.toString(amount));
        }
    }
}
