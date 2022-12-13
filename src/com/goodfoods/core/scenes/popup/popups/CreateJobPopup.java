package com.goodfoods.core.scenes.popup.popups;

import com.goodfoods.core.objects.*;
import com.goodfoods.core.objects.nodes.StyledButton;
import com.goodfoods.core.objects.nodes.StyledText;
import com.goodfoods.core.objects.nodes.StyledTextField;
import com.goodfoods.core.objects.nodes.StyledTextFields;
import com.goodfoods.core.scenes.popup.Popup;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CreateJobPopup extends Popup {

    private Stage stage;

    private WorkOrder workOrder;

    Job job;

    public CreateJobPopup(Stage stage, WorkOrder workOrder) {
        super(stage);
        this.workOrder = workOrder;
        this.stage = stage;
    }

    @Override
    public void createPopup() {
        Font textFieldFont = Font.font("Helvetica", FontWeight.THIN, 14);
        Font labelFont = Font.font("Helvetica", FontWeight.THIN, 18);
        Font font = Font.font("Helvetica", FontWeight.BOLD, 24);
        StyledText text = new StyledText("New Job", 375, 50, font);



        StyledText label = new StyledText("Job:", 275, 95, labelFont);

        ComboBox<JobType> jobBox = new ComboBox<>();
        jobBox.setItems(FXCollections.observableArrayList(JobType.values()));
        jobBox.setTranslateX(325);
        jobBox.setTranslateY(75);

        StyledTextFields styledTextFields = new StyledTextFields(255,
                335,
                135,
                500,
                25,
                180,
                textFieldFont,
                labelFont,
                "Employee:", "Expected Hours:");

        StyledButton doneButton = new StyledButton("Create", 375, 450, labelFont);
        doneButton.setAction(action -> {
            if(styledTextFields.getTextFields().get(0).getNode().getText().length() == 0) {
                job = new Job(jobBox.getSelectionModel().getSelectedItem(),
                        Integer.valueOf(styledTextFields.getTextFields().get(1).getNode().getText()));
            } else {
                job = new Job(jobBox.getSelectionModel().getSelectedItem(),
                        Integer.valueOf(styledTextFields.getTextFields().get(1).getNode().getText()),
                        styledTextFields.getTextFields().get(0).getNode().getText());
            }
            super.getPopupStage().close();
        });

        styledTextFields.getTextFields().forEach(node -> {
            super.addToPane(node.getLabel());
            super.addToPane(node.getNode());
        });
        super.addToPane(text.getNode());
        super.addToPane(doneButton.getNode(), label.getNode(), jobBox);
        super.createPopup();

        super.getPopupStage().getScene().setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                doneButton.getNode().fire();
            }
        });

    }


    public Job getJob() {
        return job;
    }
}
