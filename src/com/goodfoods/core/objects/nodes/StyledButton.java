package com.goodfoods.core.objects.nodes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class StyledButton {

    private String text;
    private Button node;

    public StyledButton(String text, int x, int y, Font font) {
        this.text = text;
        node = new Button(text);
        node.setLayoutY(y);
        node.setLayoutX(x - (text.length() * font.getSize() / 3));
        node.setFont(font);
    }

    public StyledButton setAction(EventHandler<ActionEvent> event) {
        node.setOnAction(event);
        return this;
    }

    public Button getNode() {
        return node;
    }
}
