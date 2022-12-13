package com.goodfoods.core.scenes.popup;

import com.goodfoods.core.objects.nodes.StyledTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.beans.EventHandler;
import java.util.function.Predicate;

public class SearchPopup<E> extends Popup {

    private TableView<E> table;
    private TextField textField;
    private FilteredList<E> filteredList;

    ComboBox<String> chooser;

    public SearchPopup(Stage stage) {
        super(stage);
        table = new TableView<E>();
        chooser = new ComboBox<>();
        textField = new TextField();
    }

    @Override
    public void createPopup() {
        textField.setPromptText("Search");
        textField.setOnKeyReleased(event -> {
            textFieldAction(textField.getText(), chooser.getValue());
        });
        this.addToPane(table, chooser, textField);
        super.createPopup();
    }

    public SearchPopup<E> createSearchPopup() {
        super.createPopup();
        return this;
    }

    public void setTableCoords(int x, int y) {
        chooser.setTranslateX(x);
        chooser.setTranslateY(y - 35);
        textField.setTranslateX(x + 200);
        textField.setTranslateY(y - 35);
        table.setTranslateX(x);
        table.setTranslateY(y);
    }

    public void setTableCoords() {
        chooser.setTranslateX(250);
        chooser.setTranslateY(100);
        table.setTranslateX(250);
        table.setTranslateY(150);
    }


    public void setColumns(TableColumn... columns) {
        table.getColumns().addAll(columns);
        for (TableColumn column : columns) {
            chooser.getItems().add(column.getText());
        }
        chooser.setValue(columns[0].getText());
    }

    public void addItems(E... objects) {
        ObservableList<E> list = FXCollections.observableArrayList();
        list.addAll(objects);
        filteredList = new FilteredList<E>(list, slot -> true);
        table.setItems(filteredList);
    }

    public void addItems(ObservableList<E> list) {
        filteredList = new FilteredList<E>(list, slot -> true);
        table.setItems(filteredList);
    }

    public void textFieldAction(String text, String value) {
        System.out.println("Event");
    }

    public FilteredList<E> getFilteredList() {
        return filteredList;
    }

    public ObservableList<E> getSelectedItems() {
        return table.getSelectionModel().getSelectedItems();
    }

    public TableView<E> getTable() {
        return table;
    }

}
