package com.goodfoods.core.scenes.popup.popups;

import com.goodfoods.core.objects.nodes.StyledButton;
import com.goodfoods.core.objects.nodes.StyledText;
import com.goodfoods.core.scenes.popup.Popup;
import com.goodfoods.core.scenes.popup.searchpopups.SearchProductPopup;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class NewOrderPromptPopup extends Popup {

    private Stage stage;

    public NewOrderPromptPopup(Stage stage) {
        super(stage);
        this.stage = stage;
    }

    @Override
    public void createPopup() {
        Font textFieldFont = Font.font("Helvetica", FontWeight.THIN, 13);
        Font labelFont = Font.font("Helvetica", FontWeight.THIN, 16);
        Font font = Font.font("Helvetica", FontWeight.BOLD, 24);
        StyledText text = new StyledText("New Order", 375, 50, font);

        StyledButton newProduct = new StyledButton("New Product?", 375, 150, labelFont);
        newProduct.setAction(event -> {
            new NewOrderPopup(stage).createPopup();
            super.closeWindow();
        });

        StyledButton existingProduct = new StyledButton("Existing Product?", 500, 150, labelFont);
        existingProduct.setAction(event -> {
            new SearchProductPopup(stage).createPopup();
            super.closeWindow();
        });

        super.addToPane(newProduct.getNode());
        super.createPopup();
    }
}
