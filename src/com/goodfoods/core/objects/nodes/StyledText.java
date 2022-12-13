package com.goodfoods.core.objects.nodes;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class StyledText {

    private String text;
    private Text node;
    private final Font font = Font.font("Helvetica", FontWeight.BOLD, 12);

    public StyledText(String text, double x, double y, Font font) {
        this.text = text;
        node = new Text(text);
        node.setY(y);
        node.setX(x - (text.length() * font.getSize() /3.5));
        node.setFont(font);
    }

    public StyledText(String text, double x, double y) {
        this.text = text;
        node = new Text(text);
        node.setY(y);
        node.setX(x - (text.length() * font.getSize() /2.5));
        node.setFont(font);
    }

    public Text getNode() {
        return node;
    }



}
