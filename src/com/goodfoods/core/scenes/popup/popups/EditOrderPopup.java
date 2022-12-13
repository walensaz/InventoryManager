package com.goodfoods.core.scenes.popup.popups;

import com.goodfoods.core.objects.Inventory;
import com.goodfoods.core.objects.InventoryItem;
import com.goodfoods.core.objects.Order;
import com.goodfoods.core.objects.WorkOrder;
import com.goodfoods.core.objects.nodes.StyledButton;
import com.goodfoods.core.objects.nodes.StyledText;
import com.goodfoods.core.objects.nodes.StyledTextFields;
import com.goodfoods.core.scenes.popup.Popup;
import com.goodfoods.core.scenes.popup.searchpopups.SearchInventoryPopup;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class EditOrderPopup extends Popup {

    private Stage stage;

    private Order originalProduct;

    private Order product;

    private TableView<InventoryItem> materialsView;

    private TableView<WorkOrder> workOrderView;

    private List<InventoryItem> materials;

    private StyledTextFields styledTextFields;


    public EditOrderPopup(Stage stage, Order product) {
        super(stage);
        originalProduct = product;
        this.stage = stage;
    }

    @Override
    public void createPopup() {

        this.product = originalProduct.clone();
        materials = product.getMaterials();
        materialsView = new TableView<>();
        workOrderView = new TableView<>();

        materialsView.setContextMenu(createMenu(materialsView, materials));
        workOrderView.setContextMenu(createMenu(workOrderView, product.getWorkOrders()));

        Font textFieldFont = Font.font("Helvetica", FontWeight.THIN, 13);
        Font labelFont = Font.font("Helvetica", FontWeight.THIN, 16);
        Font font = Font.font("Helvetica", FontWeight.BOLD, 24);
        StyledText text = new StyledText("Order", 500, 50, font);

        StyledButton createOrder = new StyledButton("Sumbit", 500, 650, labelFont);
        createOrder.setAction(event -> {
            this.getPopupStage().close();
        });

        StyledButton addMaterialsButton = new StyledButton("+", 938, 475, labelFont);
        addMaterialsButton.setAction(event -> {
            SearchInventoryPopup popup = new SearchInventoryPopup(stage).createSearchPopup();
            popup.setOnClose(event1 -> {
                popup.getSelectedItems().forEach(item -> {
                    InventoryItem newitem = item.clone();
                    newitem.setQuantityNeeded(0);
                    newitem.setQuantityInHouse(0);
                    materials.add(newitem);
                });
                refreshMaterials();
            });
        });

        StyledButton addWorkOrderButton = new StyledButton("+", 622, 475, labelFont);
        addWorkOrderButton.setAction(event -> {
            AddWorkOrderPopup popup = new AddWorkOrderPopup(super.getPopupStage(), this.product);
            popup.setOnClose(close -> {
                refreshWorkOrders();
            });
            popup.createPopup();
        });


        StyledButton addWorkOrder = new StyledButton("Add Work Order", 490, 75, labelFont);
        addWorkOrder.setAction(event -> {
            this.getPopupStage().close();
        });

        workOrderView.setPrefSize(300, 400);
        workOrderView.setTranslateX(350);
        workOrderView.setTranslateY(75);

        materialsView.setPrefSize(300, 400);
        materialsView.setTranslateX(665);
        materialsView.setTranslateY(75);

        TableColumn<InventoryItem, String> description = new TableColumn<>("Item");
        description.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        TableColumn<InventoryItem, Integer> quantity = new TableColumn<>("Amount");
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantityNeeded"));
        TableColumn<InventoryItem, String> total = new TableColumn<>("Total");
        total.setCellValueFactory(callback -> new SimpleStringProperty(String.valueOf(callback.getValue().getQuantityNeeded() * product.getProduct().getQuantityNeeded())));

        TableColumn<WorkOrder, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("workOrderId"));
        TableColumn<WorkOrder, String> work = new TableColumn<>("Jobs");
        work.setCellValueFactory(new PropertyValueFactory<>("workString"));

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

        materialsView.getColumns().addAll(description, quantity, total);
        workOrderView.getColumns().addAll(id, work);

        materialsView.setItems(FXCollections.observableArrayList(materials));
        workOrderView.setItems(FXCollections.observableArrayList(product.getWorkOrders()));


        styledTextFields = new StyledTextFields(85,
                140,
                75,
                500,
                25,
                180,
                textFieldFont,
                labelFont,
                "Product name:", "Product ID:", "Quantity:", "Cost:");

        CheckBox isDoneBox = new CheckBox("Is Completed?");
        isDoneBox.setTranslateX(140);
        isDoneBox.setTranslateY(270);
        if (product.isDone()) isDoneBox.setSelected(true);
        isDoneBox.setOnAction(action -> {
            if (isDoneBox.isSelected()) {
                isDoneBox.setSelected(true);
                return;
            }
            if (Inventory.getInstance().removeQuantities(product.getMaterials())) {
                isDoneBox.setSelected(true);
            } else {
                isDoneBox.setSelected(false);
            }
        });

        styledTextFields.getTextFields().get(0).getNode().setText(product.getProduct().getItemDescription());
        styledTextFields.getTextFields().get(1).getNode().setText(product.getProduct().getPartNumber());
        styledTextFields.getTextFields().get(2).getNode().setText(String.valueOf(product.getProduct().getQuantityNeeded()));
        styledTextFields.getTextFields().get(3).getNode().setText(String.valueOf(product.getProduct().getCost()));

        quantity.setOnEditCommit(event -> {
            materials.forEach(item -> {
                if (item.equals(event.getRowValue())) {
                    item.setQuantityNeeded(event.getNewValue());
                }
            });
            refreshMaterials();
        });

        super.addToPane(text.getNode());
        super.addToPane(isDoneBox);
        styledTextFields.getTextFields().forEach(node -> {
            super.addToPane(node.getLabel());
            super.addToPane(node.getNode());
        });
        super.addToPane(materialsView, createOrder.getNode(), addWorkOrder.getNode(), addMaterialsButton.getNode(), workOrderView, addWorkOrderButton.getNode());
        super.createPopup(1000, 950);

        super.getPopupStage().getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (super.getPopupStage().getScene().getFocusOwner() instanceof Button) {
                    Button button = (Button) super.getPopupStage().getScene().getFocusOwner();
                    button.fire();
                }
            }
        });

    }


    private <T> ContextMenu createMenu(TableView<T> tableView, List<T> list) {

        MenuItem editItem = new MenuItem("Edit");
        MenuItem removeItem = new MenuItem("Remove");

        removeItem.setOnAction(action -> {
            T order = tableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.NONE, "Delete?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                list.remove(order);
                tableView.setItems(FXCollections.observableArrayList(list));
                tableView.refresh();
            }
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().add(removeItem);
        return menu;
    }

    public void refreshMaterials() {
        materialsView.setItems(FXCollections.observableArrayList(materials));
        styledTextFields.getTextFields().get(3).getNode().setText(product.getCostAsString());
        materialsView.refresh();
    }

    public void refreshWorkOrders() {
        workOrderView.setItems(FXCollections.observableArrayList(this.product.getWorkOrders()));
        workOrderView.refresh();
    }

    public void changeOrder() {
        Inventory.getInstance().getCurrentOrders().remove(originalProduct);
        Inventory.getInstance().getCurrentOrders().add(product);
    }

    public boolean hasChanged() {
        product.setItemName(styledTextFields.getTextFields().get(0).getNode().getText());
        product.setItemId(styledTextFields.getTextFields().get(1).getNode().getText());
        product.getProduct().setQuantityNeeded(Integer.valueOf(styledTextFields.getTextFields().get(2).getNode().getText()));
        product.getProduct().setCost(Float.valueOf(styledTextFields.getTextFields().get(3).getNode().getText()));
        product.setMaterials(materials);
        return !originalProduct.equals(product);
    }

}
