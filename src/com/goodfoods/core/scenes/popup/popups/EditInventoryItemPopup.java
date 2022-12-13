package com.goodfoods.core.scenes.popup.popups;

import com.goodfoods.core.objects.Inventory;
import com.goodfoods.core.objects.InventoryItem;
import com.goodfoods.core.objects.nodes.StyledButton;
import com.goodfoods.core.objects.nodes.StyledText;
import com.goodfoods.core.objects.nodes.StyledTextFields;
import com.goodfoods.core.scenes.popup.Popup;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EditInventoryItemPopup extends Popup {

    StyledTextFields styledTextFields;

    StyledTextFields styledTextFields2;

    InventoryItem item;

    public EditInventoryItemPopup(Stage stage, InventoryItem item) {
        super(stage);
        this.item = item;
    }

    @Override
    public void createPopup() {
        Font textFieldFont = Font.font("Helvetica", FontWeight.THIN, 13);
        Font labelFont = Font.font("Helvetica", FontWeight.THIN, 16);
        Font font = Font.font("Helvetica", FontWeight.BOLD, 24);
        StyledText text = new StyledText("Edit Inventory Item", 375, 50, font);

        StyledButton submit = new StyledButton("Sumbit Edit", 375, 700, Font.font("Helvetica", FontWeight.THIN, 16));
        submit.getNode().setOnAction(action -> {
            this.getPopupStage().close();
        });

         styledTextFields = new StyledTextFields(85,
                140,
                75,
                500,
                25,
                180,
                textFieldFont,
                labelFont,
                "Product name:", "Product ID:", "Quantity:", "Price:");

        styledTextFields.getTextFields().get(0).getNode().setText(item.getItemDescription());
        styledTextFields.getTextFields().get(1).getNode().setText(item.getPartNumber());
        styledTextFields.getTextFields().get(2).getNode().setText(String.valueOf(item.getQuantityInHouse()));
        styledTextFields.getTextFields().get(3).getNode().setText(String.valueOf(item.getCost()));

        styledTextFields2 = new StyledTextFields(400,
                440,
                75,
                500,
                25,
                180,
                textFieldFont,
                labelFont,
                "Vendor:", "Min Quantity:");

        styledTextFields2.getTextFields().get(0).getNode().setText(item.getVendor());
        styledTextFields2.getTextFields().get(1).getNode().setText(String.valueOf(item.getMinQty()));

        super.addToPane(text.getNode());
        styledTextFields.getTextFields().forEach(node -> {
            super.addToPane(node.getLabel());
            super.addToPane(node.getNode());
        });
        styledTextFields2.getTextFields().forEach(node -> {
            super.addToPane(node.getLabel());
            super.addToPane(node.getNode());
        });
        super.addToPane(submit.getNode());
        super.createPopup();

        super.getPopupStage().getScene().setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                submit.getNode().fire();
            }
        });
    }

    public void addNewItem() {
        Inventory.getInstance().getInventory().remove(item);
        item = item.clone();
        item.setItemDescription(styledTextFields.getTextFields().get(0).getNode().getText());
        item.setPartNumber(styledTextFields.getTextFields().get(1).getNode().getText());
        item.setQuantityInHouse(Integer.parseInt(styledTextFields.getTextFields().get(2).getNode().getText()));
        item.setCost(Float.parseFloat(styledTextFields.getTextFields().get(3).getNode().getText()));
        item.setVendor(styledTextFields2.getTextFields().get(0).getNode().getText());
        item.setMinQty(Integer.parseInt(styledTextFields2.getTextFields().get(1).getNode().getText()));
        Inventory.getInstance().addItem(item);
    }

    public boolean hasChanged() {
        InventoryItem temp = item.clone();
        temp.setItemDescription(styledTextFields.getTextFields().get(0).getNode().getText());
        temp.setPartNumber(styledTextFields.getTextFields().get(1).getNode().getText());
        temp.setQuantityInHouse(Integer.parseInt(styledTextFields.getTextFields().get(2).getNode().getText()));
        temp.setCost(Float.parseFloat(styledTextFields.getTextFields().get(3).getNode().getText()));
        temp.setVendor(styledTextFields2.getTextFields().get(0).getNode().getText());
        temp.setMinQty(Integer.parseInt(styledTextFields2.getTextFields().get(1).getNode().getText()));
        return !(item.equals(temp));
    }





}
