package com.goodfoods.core.scenes;

import com.goodfoods.core.Launcher;
import com.goodfoods.core.SceneController;
import com.goodfoods.core.objects.Inventory;
import com.goodfoods.core.objects.InventoryItem;
import com.goodfoods.core.objects.Order;
import com.goodfoods.core.scenes.popup.popups.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class InventoryController implements Initializable {

    ContextMenu inventoryMenu = new ContextMenu();
    ContextMenu ordersMenu = new ContextMenu();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initUpdater();
        initInventoryTab();
        initOrderTab();
    }

    @FXML
    TabPane tabPane;


    //Work Orders Tab
    @FXML
    private Button newOrderButton;

    @FXML
    private TableView<Order> workOrderTable;

    @FXML
    private TableColumn<Order, String> workOrderIdColumn, workItemIdColumn, workDescriptionColumn, workVendorColumn;

    public void makePopUp() {
        new NewOrderPopup(SceneController.getInstance().getStage()).createPopup();
    }



    //Inventory Tab
    private ObservableList<InventoryItem> inventory;

    boolean option;

    @FXML
    Label errorText;

    @FXML
    TableView<InventoryItem> inventoryTable;

    @FXML
    TableColumn<InventoryItem, String> partNumberColumn, itemDescriptionColumn, vendorColumn, PONumberColumn;

    @FXML
    TableColumn<InventoryItem, Integer> quantityHaveColumn;

    @FXML
    TableColumn<InventoryItem, Float> quantityStatsColumn, costColumn, quantityNeededColumn, quantityInUseColumn;

    @FXML
    TableColumn<InventoryItem, Integer> minQtyColumn;

    @FXML
    Label newProductName, newProductNumber, newProductQuantity, newProductPrice, newProductVendor;

    @FXML
    TextField newProductNameText, newProductNumberText, newProductQuantityText, newProductVendorText, newProductPriceText;

    @FXML
    Button newProductButton, createProductButton, exportButton, importButton;

    @FXML
    public void makeNewProductUI() {
         setNewProductFieldsVisible(true);
    }

    @FXML
    public void makeNewProduct() {
        String name = newProductNameText.getText();
        float cost = Float.parseFloat(newProductPriceText.getText());
        String number = newProductNumberText.getText();
        int quantity = Integer.parseInt(newProductQuantityText.getText());
        float price = Float.parseFloat(newProductPriceText.getText());
        String vendor = newProductVendorText.getText();
        boolean inventoryAlreadyContains = inventory.stream().filter(item -> item.getPartNumber().equalsIgnoreCase(number)).count() >= 1;
        inventoryAlreadyContains = inventoryAlreadyContains ? inventoryAlreadyContains : inventory.stream().filter(item -> item.getItemDescription().equalsIgnoreCase(name)).count() >= 1;
        if(!option && inventoryAlreadyContains) {
            option = true;
            errorText.setText("The database already contains an item with this name or id, would you still like to continue?");
            return;
        } else {
            errorText.setText("");
            option = false;
        }
        inventory.add(new InventoryItem(number, name, quantity, price, vendor));
        Inventory.getInstance().reCalculate();
        inventoryTable.setItems(inventory);
        setNewProductFieldsVisible(false);
    }

    public void initOrderTab() {
        workOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        workItemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        workDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        MenuItem editItem = new MenuItem("Edit");
        MenuItem addItem = new MenuItem("New");
        MenuItem removeItem = new MenuItem("Remove");
        editItem.setOnAction(action -> {
            EditOrderPopup popup = new EditOrderPopup(SceneController.getInstance().getStage(), workOrderTable.getSelectionModel().getSelectedItem());
            popup.setOnClose(event -> {
                if(popup.hasChanged()) {
                    Alert alert = new Alert(Alert.AlertType.NONE, "Update Order?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        popup.changeOrder();
                    }
                }
                this.workOrderTable.refresh();
            });
            popup.createPopup();
        });

        removeItem.setOnAction(action -> {
            InventoryItem item = inventoryTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.NONE, "Delete " + item.getItemDescription() + " from inventory?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                inventory.remove(item);
            }
        });

        addItem.setOnAction(action -> {
            new NewInventoryItemPopup(SceneController.getInstance().getStage()).createPopup();
        });

        ordersMenu.getItems().addAll(editItem);
        workOrderTable.setContextMenu(ordersMenu);

        workOrderTable.setItems(Inventory.getInstance().getCurrentOrders());
    }

    public void initInventoryTab() {
        inventory = Inventory.getInstance().getInventory();
        option = false;

        partNumberColumn.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
        itemDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        quantityHaveColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInHouse"));
        quantityNeededColumn.setCellValueFactory(new PropertyValueFactory<>("quantityNeeded"));
        minQtyColumn.setCellValueFactory(new PropertyValueFactory<>("minQty"));
        PONumberColumn.setCellValueFactory(new PropertyValueFactory<>("PONumber"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        vendorColumn.setCellValueFactory(new PropertyValueFactory<>("vendor"));
        quantityInUseColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInUse"));
        inventoryTable.setEditable(true);

        minQtyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                return Integer.valueOf(string);
            }
        }));
        minQtyColumn.setOnEditCommit(event -> {
            inventory.forEach(item -> {
                if (item.equals(event.getRowValue())) {
                    item.setMinQty(event.getNewValue());
                }
            });
            Inventory.getInstance().reCalculate();
            inventoryTable.refresh();
        });

        quantityHaveColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                return Integer.valueOf(string);
            }
        }));
        quantityHaveColumn.setOnEditCommit(event -> {
            inventory.forEach(item -> {
                if (item.equals(event.getRowValue())) {
                    item.setQuantityInHouse(event.getNewValue());
                }
            });
            Inventory.getInstance().reCalculate();
            inventoryTable.refresh();
        });


        MenuItem editItem = new MenuItem("Edit");
        MenuItem addItem = new MenuItem("New");
        MenuItem removeItem = new MenuItem("Remove");
        editItem.setOnAction(action -> {
            EditInventoryItemPopup popup = new EditInventoryItemPopup(SceneController.getInstance().getStage(), inventoryTable.getSelectionModel().getSelectedItem());
            popup.setOnClose(event -> {
                if(popup.hasChanged()) {
                    InventoryItem item = inventoryTable.getSelectionModel().getSelectedItem();
                    Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to edit " + item.getItemDescription() + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        popup.addNewItem();
                    }
                    Inventory.getInstance().reCalculate();
                    inventoryTable.refresh();
                }
            });
            popup.createPopup();
        });

        removeItem.setOnAction(action -> {
            InventoryItem item = inventoryTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.NONE, "Delete " + item.getItemDescription() + " from inventory?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                inventory.remove(item);
            }
            inventoryTable.refresh();
        });

        addItem.setOnAction(action -> {
            NewInventoryItemPopup popup = new NewInventoryItemPopup(SceneController.getInstance().getStage());
            popup.setOnClose(event -> {
                inventoryTable.refresh();
                Inventory.getInstance().reCalculate();
            });
            popup.createPopup();
        });

        inventoryMenu.getItems().addAll(addItem);
        inventoryTable.setContextMenu(inventoryMenu);

        inventoryTable.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.SECONDARY)) {
                if(inventoryTable.getSelectionModel().getSelectedItems().size() > 0) {
                    if(inventoryMenu.getItems().size() < 2) {
                        inventoryMenu.getItems().addAll(editItem, removeItem);
                    }
                } else {
                    if(editItem.getParentMenu() != null) {
                        inventoryMenu.getItems().removeAll(editItem, removeItem);
                    }
                }
            }
        });
        inventoryTable.setItems(Inventory.getInstance().getInventory());
    }



    public void setNewProductFieldsVisible(boolean visible) {
        newProductName.setVisible(visible);
        newProductNumber.setVisible(visible);
        newProductQuantity.setVisible(visible);
        newProductPrice.setVisible(visible);
        newProductVendor.setVisible(visible);
        createProductButton.setVisible(visible);
        newProductButton.setVisible(!visible);

        newProductPriceText.setText("");
        newProductNameText.setText("");
        newProductNumberText.setText("");
        newProductQuantityText.setText("");
        newProductPriceText.setText("");
        newProductVendorText.setText("");
    }

    public void update() {
        inventoryTable.refresh();
    }

    public void initUpdater() {
        //Launcher.executor.scheduleAtFixedRate(this::update, 500L, 500L, TimeUnit.MILLISECONDS);
    }



}
