package com.goodfoods.core.scenes.popup.popups;

import com.goodfoods.core.SceneController;
import com.goodfoods.core.objects.Inventory;
import com.goodfoods.core.objects.InventoryItem;
import com.goodfoods.core.objects.Order;
import com.goodfoods.core.objects.nodes.StyledButton;
import com.goodfoods.core.objects.nodes.StyledText;
import com.goodfoods.core.objects.nodes.StyledTextFields;
import com.goodfoods.core.scenes.popup.Popup;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class ConfirmNewOrderPopup extends Popup {

    private Stage stage;

    private Order product;

    private List<InventoryItem> materials;

    public ConfirmNewOrderPopup(Stage stage, Order product) {
        super(stage);
        this.product = product;
        this.stage = stage;
    }

    @Override
    public void createPopup() {
        materials = product.getMaterials();
        Font textFieldFont = Font.font("Helvetica", FontWeight.THIN, 13);
        Font labelFont = Font.font("Helvetica", FontWeight.THIN, 16);
        Font font = Font.font("Helvetica", FontWeight.BOLD, 24);
        StyledText text = new StyledText("New Order", 375, 50, font);

        StyledButton createOrder = new StyledButton("Create Order", 375, 500, labelFont);
        createOrder.setAction(event -> {
            Inventory.getInstance().addOrder(product);
            Inventory.getInstance().addProduct(product.getProduct());
            EditOrderPopup popup = new EditOrderPopup(SceneController.getInstance().getStage(), product);
            popup.createPopup();
            Inventory.getInstance().addItemsInUse(product.getMaterials());
            this.getPopupStage().close();
        });

        TableView<InventoryItem> materialsView = new TableView<>();
        materialsView.setPrefSize(250, 250);
        materialsView.setTranslateX(450);
        materialsView.setTranslateY(150);

        TableColumn<InventoryItem, String> description = new TableColumn<>("Item");
        description.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        TableColumn<InventoryItem, Integer> quantity = new TableColumn<>("Amount");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantityNeeded"));

        materialsView.setEditable(true);

        quantity.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                return Integer.valueOf(string);
            }
        }));

        materialsView.getColumns().addAll(description, quantity);

        materialsView.setItems(FXCollections.observableArrayList(materials));

        StyledTextFields styledTextFields = new StyledTextFields(85,
                140,
                75,
                500,
                25,
                180,
                textFieldFont,
                labelFont,
                "Product name:", "Product ID:", "Quantity:", "Price:");

        styledTextFields.getTextFields().get(0).getNode().setText(product.getProduct().getItemDescription());
        styledTextFields.getTextFields().get(1).getNode().setText(product.getProduct().getPartNumber());
        styledTextFields.getTextFields().get(2).getNode().setText(String.valueOf(product.getProduct().getQuantityNeeded()));
        styledTextFields.getTextFields().get(3).getNode().setText(String.valueOf(product.getProduct().getCost()));

        quantity.setOnEditCommit(event -> {
            materials.forEach(item -> {
                if(item.equals(event.getRowValue())) {
                    item.setQuantityNeeded(event.getNewValue());
                    product.getProduct().setCost(product.getCost());
                    styledTextFields.getTextFields().get(3).getNode().setText(String.valueOf(product.getCostAsString()));
                }
            });
        });

        super.addToPane(text.getNode());
        styledTextFields.getTextFields().forEach(node -> {
            super.addToPane(node.getLabel());
            super.addToPane(node.getNode());
        });
        super.addToPane(materialsView, createOrder.getNode());
        super.createPopup();

    }
}
