package imat;

// -- Imports -- //
import imat.MainViewItemDetail;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class MainViewItem extends AnchorPane {

    IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();
    private Product product;
    private MainViewItemDetail mainViewItemDetail;
    private int amount = 0;
    @FXML
    private ImageView itemImageView;
    @FXML
    private Text itemNameText;
    @FXML
    private Text itemPriceLabel;
    @FXML
    private TextField itemAmountTextField;



    // -- Constructor -- //
    public MainViewItem(Product product){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main_view_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        this.mainViewItemDetail = new MainViewItemDetail(product);

        itemImageView.setImage(iMatDataHandler.getFXImage(product));
        itemNameText.setText(product.getName());
        itemPriceLabel.setText(Double.toString(product.getPrice()));
        itemAmountTextField.setText(Integer.toString(amount));

    }

    // -- Methods -- //

    public void popUpItemDetail() {

    }
    public void increaseAmount() {
        amount += 1;
        itemAmountTextField.setText(Integer.toString(amount));
    }

    public void decreaseAmount() {
        if(amount>=1){
            amount -= 1;
            itemAmountTextField.setText(Integer.toString(amount));
        }
    }

}



