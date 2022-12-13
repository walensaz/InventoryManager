package com.goodfoods.core.scenes.popup.popups;

import com.goodfoods.core.SceneController;
import com.goodfoods.core.objects.*;
import com.goodfoods.core.objects.nodes.StyledButton;
import com.goodfoods.core.objects.nodes.StyledText;
import com.goodfoods.core.objects.nodes.StyledTextFields;
import com.goodfoods.core.scenes.popup.Popup;
import com.goodfoods.core.scenes.popup.searchpopups.SearchInventoryPopup;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AddWorkOrderPopup  extends Popup {

    private Stage stage;

    private List<InventoryItem> materials;

    private TableView<Job> jobView;

    private WorkOrder workOrder;

    private Order order;

    public AddWorkOrderPopup(Stage stage, Order order) {
        super(stage);
        this.stage = stage;
        workOrder = new WorkOrder(order);
        this.order = order;
    }

    @Override
    public void createPopup() {
        materials = new ArrayList<>();
        Font textFieldFont = Font.font("Helvetica", FontWeight.THIN, 13);
        Font labelFont = Font.font("Helvetica", FontWeight.THIN, 16);
        Font font = Font.font("Helvetica", FontWeight.BOLD, 24);
        StyledText text = new StyledText("New Work Order", 375, 50, font);

        jobView = new TableView<>();

        jobView.setPrefSize(250, 300);
        jobView.setTranslateX(450);
        jobView.setTranslateY(100);

        TableColumn<Job, JobType> jobTypeColumn = new TableColumn<>("Job");
        jobTypeColumn.setCellValueFactory(new PropertyValueFactory<>("jobType"));
        TableColumn<Job, Integer> hoursColumn = new TableColumn<>("Hours*");
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("expectedHours"));

        jobView.setEditable(true);

        jobView.getColumns().addAll(jobTypeColumn, hoursColumn);


        StyledButton addJobButton = new StyledButton("+", 678, 400, labelFont);
        addJobButton.setAction(event -> {
            CreateJobPopup popup = new CreateJobPopup(super.getPopupStage(), workOrder);
            popup.setOnClose(close -> {
                workOrder.addWork(popup.getJob());
                refreshView();
            });
            popup.createPopup();
        });

        StyledButton doneButton = new StyledButton("Create", 375, 450, labelFont);

        doneButton.setAction(action -> {
            order.addWorkOrder(workOrder);
            super.getPopupStage().close();
        });

        super.addToPane(text.getNode());
        super.addToPane(doneButton.getNode(), jobView, addJobButton.getNode());
        super.createPopup();

        super.getPopupStage().getScene().setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                doneButton.getNode().fire();
            }
        });

    }

    public void refreshView() {
        jobView.setItems(FXCollections.observableArrayList(workOrder.getWork()));
        jobView.refresh();
    }

}
