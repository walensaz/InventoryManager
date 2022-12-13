package com.goodfoods.core.scenes.popup.popups;

import com.goodfoods.core.SceneController;
import com.goodfoods.core.objects.Inventory;
import com.goodfoods.core.objects.InventoryItem;
import com.goodfoods.core.objects.Order;
import com.goodfoods.core.objects.nodes.StyledButton;
import com.goodfoods.core.objects.nodes.StyledText;
import com.goodfoods.core.objects.nodes.StyledTextFields;
import com.goodfoods.core.scenes.popup.Popup;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class NewInventoryItemPopup extends Popup {

    public NewInventoryItemPopup(Stage stage) {
        super(stage);
    }

    @Override
    public void createPopup() {
        Font textFieldFont = Font.font("Helvetica", FontWeight.THIN, 13);
        Font labelFont = Font.font("Helvetica", FontWeight.THIN, 16);
        Font font = Font.font("Helvetica", FontWeight.BOLD, 24);
        StyledText text = new StyledText("New Inventory Item", 375, 50, font);

        StyledTextFields styledTextFields = new StyledTextFields(85,
                140,
                75,
                500,
                25,
                180,
                textFieldFont,
                labelFont,
                "Product name:", "Product ID:", "Quantity:", "Price:");

        StyledTextFields styledTextFields2 = new StyledTextFields(400,
                440,
                75,
                500,
                25,
                180,
                textFieldFont,
                labelFont,
                "Vendor:");

        StyledButton doneButton = new StyledButton("Add", 375, 450, labelFont);
        doneButton.setAction(event -> {
            ObservableList<InventoryItem> inventory = Inventory.getInstance().getInventory();
            boolean inventoryAlreadyContains = inventory.stream().filter(item -> item.getPartNumber().equalsIgnoreCase(styledTextFields.getTextFields().get(1).getNode().getText())).count() >= 1;
            inventoryAlreadyContains = inventoryAlreadyContains ? inventoryAlreadyContains : inventory.stream().filter(item -> item.getItemDescription().equalsIgnoreCase(styledTextFields.getTextFields().get(0).getNode().getText())).count() >= 1;
            if(inventoryAlreadyContains) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "An item already exists with this name or id.  Would you like to continue?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    Inventory.getInstance().addItem(new InventoryItem(styledTextFields.getTextFields().get(1).getNode().getText(),
                            styledTextFields.getTextFields().get(0).getNode().getText(),
                            Integer.parseInt(styledTextFields.getTextFields().get(2).getNode().getText()),
                            Float.parseFloat(styledTextFields.getTextFields().get(3).getNode().getText()),
                            styledTextFields2.getTextFields().get(0).getNode().getText()));
                    super.getPopupStage().close();
                }
            } else {
                Inventory.getInstance().addItem(new InventoryItem(styledTextFields.getTextFields().get(1).getNode().getText(),
                        styledTextFields.getTextFields().get(0).getNode().getText(),
                        Integer.parseInt(styledTextFields.getTextFields().get(2).getNode().getText()),
                        Float.parseFloat(styledTextFields.getTextFields().get(3).getNode().getText()),
                        styledTextFields2.getTextFields().get(0).getNode().getText()));
                super.getPopupStage().close();
            }
        });


        super.addToPane(text.getNode());
        styledTextFields.getTextFields().forEach(node -> {
            super.addToPane(node.getLabel());
            super.addToPane(node.getNode());
        });
        styledTextFields2.getTextFields().forEach(node -> {
            super.addToPane(node.getLabel());
            super.addToPane(node.getNode());
        });
        super.addToPane(doneButton.getNode());
        super.createPopup();

        super.getPopupStage().getScene().setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                doneButton.getNode().fire();
            }
        });

    }
}
