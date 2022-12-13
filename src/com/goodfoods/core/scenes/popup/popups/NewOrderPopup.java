package com.goodfoods.core.scenes.popup.popups;

import com.goodfoods.core.objects.InventoryItem;
import com.goodfoods.core.objects.Order;
import com.goodfoods.core.objects.nodes.StyledButton;
import com.goodfoods.core.objects.nodes.StyledText;
import com.goodfoods.core.objects.nodes.StyledTextFields;
import com.goodfoods.core.scenes.popup.Popup;
import com.goodfoods.core.scenes.popup.searchpopups.SearchInventoryPopup;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class NewOrderPopup extends Popup {

    private Stage stage;

    private List<InventoryItem> materials;

    public NewOrderPopup(Stage stage) {
        super(stage);
        this.stage = stage;
    }

    @Override
    public void createPopup() {
        materials = new ArrayList<>();
        Font textFieldFont = Font.font("Helvetica", FontWeight.THIN, 13);
        Font labelFont = Font.font("Helvetica", FontWeight.THIN, 16);
        Font font = Font.font("Helvetica", FontWeight.BOLD, 24);
        StyledText text = new StyledText("New Order", 375, 50, font);

        StyledTextFields styledTextFields = new StyledTextFields(85,
                140,
                75,
                500,
                25,
                180,
                textFieldFont,
                labelFont,
                "Product name:", "Product ID:", "Quantity:");

        StyledButton doneButton = new StyledButton("Done", 375, 450, labelFont);
        doneButton.setAction(event -> {
            new ConfirmNewOrderPopup(stage, new Order(new InventoryItem(styledTextFields.getTextFields().get(1).getNode().getText(),
                    styledTextFields.getTextFields().get(0).getNode().getText(),
                    Integer.parseInt(styledTextFields.getTextFields().get(2).getNode().getText())), materials)).createPopup();
            super.getPopupStage().close();
        });


        StyledButton addMaterialsButton = new StyledButton("Add Materials", 440, 200, labelFont);
        addMaterialsButton.setAction(event -> {
            SearchInventoryPopup popup = new SearchInventoryPopup(stage).createSearchPopup();
            popup.setOnClose(event1 -> materials = popup.getSelectedItems());
        });

        super.addToPane(text.getNode());
        styledTextFields.getTextFields().forEach(node -> {
            super.addToPane(node.getLabel());
            super.addToPane(node.getNode());
        });
        super.addToPane(addMaterialsButton.getNode(), doneButton.getNode());
        super.createPopup();

        stage.getScene().setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                doneButton.getNode().fire();
            }
        });

    }

}
