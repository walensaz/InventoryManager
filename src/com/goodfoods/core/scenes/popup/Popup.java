package com.goodfoods.core.scenes.popup;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Popup {

    private Stage applicationStage;

    private Stage popupStage;

    private Pane pane;

    public Popup(Stage stage) {
        this.applicationStage = stage;
        this.popupStage = new Stage();
        pane = new Pane();
        this.popupStage.initOwner(applicationStage);
        this.popupStage.setOnCloseRequest(event -> onClose());
    }

    public void createPopup() {
        Scene popupScene = new Scene(pane, 750, 750);
        popupStage.setScene(popupScene);
        popupStage.show();
        popupStage.getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (popupStage.getScene().getFocusOwner() instanceof Button) {
                    Button button = (Button) popupStage.getScene().getFocusOwner();
                    button.fire();
                }
            }
        });
    }

    public void createPopup(int width, int height) {
        Scene popupScene = new Scene(pane, width, height);
        popupStage.setScene(popupScene);
        popupStage.show();
        popupStage.getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (popupStage.getScene().getFocusOwner() instanceof Button) {
                    Button button = (Button) popupStage.getScene().getFocusOwner();
                    button.fire();
                }
            }
        });
    }

    public void onClose() {
    }


    public Stage getApplicationStage() {
        return applicationStage;
    }

    public Stage getPopupStage() {
        return popupStage;
    }

    public Pane getPane() {
        return pane;
    }

    public void addToPane(Node... nodes) {
        pane.getChildren().addAll(nodes);
    }

    public Popup getAndAdd(Node... nodes) {
        pane.getChildren().addAll(nodes);
        return this;
    }

    public void closeWindow() {
        this.popupStage.close();
    }

    public void setOnClose(javafx.event.EventHandler<WindowEvent> event) {
        getPopupStage().setOnHiding(event);
    }



}
