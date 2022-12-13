package com.goodfoods.core.objects.nodes;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StyledTextField {

    private TextField node;
    private Text text;

    public StyledTextField(String label, int x, int y, int height, int width, Font textFieldFont,Font labelFont) {
        text = new StyledText(label,(x), y + 15, labelFont).getNode();
        node = new TextField();
        node.setTranslateY(y);
        node.setTranslateX(x + (label.length() * labelFont.getSize() /3.5));
        node.setFont(textFieldFont);
        node.setPrefWidth(width);
        node.setPrefHeight(height);
    }

    public StyledTextField(String label, int labelx, int labely, int textfieldx, int textfieldy, int height, int width, Font textFieldFont,Font labelFont) {
        text = new StyledText(label,(labelx), labely + 15, labelFont).getNode();
        node = new TextField();
        node.setTranslateY(textfieldy);
        node.setTranslateX(textfieldx);
        node.setFont(textFieldFont);
        node.setPrefWidth(width);
        node.setPrefHeight(height);
    }

    public TextField getNode() {
        return node;
    }

    public Text getLabel() { return text; }

}
