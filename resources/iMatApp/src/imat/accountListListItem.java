package imat;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class accountListListItem extends AnchorPane {
    @FXML
    private Text nameText;
    @FXML
    private FlowPane listItemOpenedFlow;
    @FXML
    private Text amountOfProductText;
    @FXML
    private Text totalPriceText;
    @FXML
    public FlowPane itemFlowPane;
    @FXML
    private ScrollPane listItemScrollPane;

    private boolean state = false;
    private MainViewController parentController;
    private ArrayList<ShoppingItem> content;
    private String name;
    private HashMap<Integer, String> monthmap;
    @FXML
    protected void addListToCart(){
        for(ShoppingItem item : this.content){
            IMatDataHandler.getInstance().getShoppingCart().addItem(item);
        }
    }
    @FXML
    protected void rightArrowClick(){
        listItemScrollPane.setHvalue(listItemScrollPane.getHvalue() + 0.0125);
    }
    @FXML
    protected void leftArrowClick(){
        listItemScrollPane.setHvalue(listItemScrollPane.getHvalue() - 0.0125);
    }

    private void updateListItems(){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("accountListItem.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
    }

    private void updateChildren(){
        nameText.setText(this.name);
        String amount = String.valueOf(this.content.size());
        amountOfProductText.setText(amount + " varor");
        String price = String.valueOf(this.content.size() * 21);
        totalPriceText.setText(price + " kr");
        for(ShoppingItem product : this.content){
            accountListListProduct listProduct = new accountListListProduct(product, this.parentController);
            itemFlowPane.getChildren().add(listProduct);
        }
    }

    public accountListListItem(String listName, ArrayList<ShoppingItem> contents, MainViewController mainController){
        this.name = listName;
        this.content = contents;

        this.parentController = mainController;
        updateListItems();
        updateChildren();

    }
}
