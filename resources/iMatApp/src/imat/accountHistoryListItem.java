package imat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Order;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.HashMap;

public class accountHistoryListItem extends AnchorPane {

    @FXML
    private Text dateText;
    @FXML
    private FlowPane historyItemOpenedFlow;
    @FXML
    private Text amountOfProductText;
    @FXML
    private Text totalPriceText;
    @FXML
    public FlowPane itemFlowPane;
    @FXML
    private ScrollPane historyItemScrollPane;

    private boolean state = false;
    private TitledPane parentController;
    private Order order;
    private HashMap<Integer, String> monthmap;
    @FXML
    protected void historyViewAllClick(){
        System.out.println("test");
        state = !state;
        updateListItems();
        updateChildren();
    }
    @FXML
    protected void rightArrowClick(){
        historyItemScrollPane.setHvalue(historyItemScrollPane.getHvalue() + 0.025);
    }
    @FXML
    protected void leftArrowClick(){
        historyItemScrollPane.setHvalue(historyItemScrollPane.getHvalue() - 0.025);
    }

    private void updateListItems(){
        if(!state){
            this.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("accountHistoryItem.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
        else{
            this.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("accountHistoryItem2.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
    private void updateChildren(){
        String month = monthmap.get(order.getDate().getMonth());
        dateText.setText(order.getDate().getDay() + " " + month + " " + (order.getDate().getYear()+1900));
        String amount = String.valueOf(Math.round(order.getItems().size()));
        amountOfProductText.setText(amount + " varor");
        String price = String.valueOf(order.getItems().size() * 21);
        totalPriceText.setText(price + " kr");
        for(ShoppingItem product : order.getItems()){
            accountListProduct historyProduct = new accountListProduct(product, parentController);
            if(!state) {
                itemFlowPane.getChildren().add(historyProduct);
            }
            else {
                historyItemOpenedFlow.getChildren().add(historyProduct);
            }
        }
    }

    public accountHistoryListItem(Order incomingOrder, TitledPane mainController, HashMap<Integer, String> monthMap) {
        this.order = incomingOrder;
        this.monthmap = monthMap;
        this.parentController = mainController;
        updateListItems();
        updateChildren();

    }

}
