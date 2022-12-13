package com.goodfoods.core.scenes.popup.searchpopups;

import com.goodfoods.core.objects.Inventory;
import com.goodfoods.core.objects.InventoryItem;
import com.goodfoods.core.objects.nodes.StyledButton;
import com.goodfoods.core.scenes.popup.SearchPopup;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SearchInventoryPopup extends SearchPopup<InventoryItem> {

    TableColumn<InventoryItem, String> partNumberColumn, itemDescriptionColumn;

    private StyledButton submit;


    public SearchInventoryPopup(Stage stage) {
        super(stage);
    }

    @Override
    public SearchInventoryPopup createSearchPopup() {
        TableView.TableViewSelectionModel<InventoryItem> tableViewSelectionModel = super.getTable().getSelectionModel();
        tableViewSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        submit = new StyledButton("Submit", 375, 700, Font.font("Helvetica", FontWeight.THIN, 16));
        submit.getNode().setOnAction(action -> {
            this.getPopupStage().close();
        });

        partNumberColumn = new TableColumn<>("Part Number");
        itemDescriptionColumn = new TableColumn<>("Item Description");

        partNumberColumn.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
        itemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));

        this.getTable().setPrefSize(500, 500);
        partNumberColumn.setPrefWidth(200);
        itemDescriptionColumn.setPrefWidth(300);
        this.setTableCoords(125, 100);

        super.setColumns(partNumberColumn, itemDescriptionColumn);
        super.addItems(Inventory.getInstance().getInventory());
        super.addToPane(submit.getNode());
        super.createPopup();

        super.getPopupStage().getScene().setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                submit.getNode().fire();
            }
        });

        return this;
    }

    @Override
    public void textFieldAction(String event, String value) {
        switch(value) {
            case "Part Number":
                super.getFilteredList().setPredicate(item -> item.getPartNumber().toLowerCase().contains(event));
                break;
            case "Item Description":
                super.getFilteredList().setPredicate(item -> item.getItemDescription().toLowerCase().contains(event.toLowerCase()));
                break;
        }
    }

}
