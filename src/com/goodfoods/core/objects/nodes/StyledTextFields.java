package com.goodfoods.core.objects.nodes;

import javafx.scene.text.Font;

import java.util.ArrayList;

public class StyledTextFields {

    private ArrayList<StyledTextField> textFields;

    public StyledTextFields(int startX, int startY, int maxY, int height, int width, Font textFieldFont, Font labelFont, String... texts) {
        textFields = new ArrayList<>();
        for (String text : texts) {
            StyledTextField textField = new StyledTextField(text, startX, startY, height, width, textFieldFont, labelFont);
            textFields.add(textField);
            startY += (height * 2);
            if(startY >= maxY) break;
        }
    }

    public StyledTextFields(int startLabelX, int startTextX,  int startY, int maxY, int height, int width, Font textFieldFont, Font labelFont, String... texts) {
        textFields = new ArrayList<>();
        for (String text : texts) {
            StyledTextField textField = new StyledTextField(text, startLabelX, startY + 5, startTextX, startY, height, width, textFieldFont, labelFont);
            textFields.add(textField);
            startY += (height * 2);
            if(startY >= maxY) break;
        }
    }

    public ArrayList<StyledTextField> getTextFields() {
        return textFields;
    }
}
