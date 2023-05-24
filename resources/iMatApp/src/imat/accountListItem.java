package imat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class accountListItem extends AnchorPane {

    @FXML
    private Text dateText;
    @FXML
    private Text amountOfProductText;
    @FXML
    private Text totalPriceText;

    private Order order;
    public accountListItem(Order incomingOrder, HashMap<Integer, String> monthMap) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("account_list_item.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.order = incomingOrder;
        String month = monthMap.get(order.getDate().getMonth());
        dateText.setText(month);
        String amount = String.valueOf(order.getItems().size());
        amountOfProductText.setText(amount);
        String price = String.valueOf(order.getItems().size() * 21);
        totalPriceText.setText(price);

    }

}
