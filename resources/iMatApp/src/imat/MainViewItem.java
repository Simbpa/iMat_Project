// This module serves as a template for all cards put into the main view grid

// -- Packages -- //
package imat;

// -- Imports -- //
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    private Label itemNameLabel;
    @FXML
    private Label itemPriceLabel;
    @FXML
    private Label itemAmountLabel;



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
        itemNameLabel.setText(product.getName());
        itemPriceLabel.setText(Double.toString(product.getPrice()));
        itemAmountLabel.setText(Integer.toString(amount));

    }

    // -- Methods -- //

    public void popUpItemDetail() {

    }
    public void increaseAmount() {
        amount += 1;
        itemAmountLabel.setText(Integer.toString(amount));
    }

    public void decreaseAmount() {
        if(amount>=1){
            amount -= 1;
            itemAmountLabel.setText(Integer.toString(amount));
        }
    }

}



