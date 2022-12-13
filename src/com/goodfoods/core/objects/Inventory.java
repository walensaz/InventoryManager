package com.goodfoods.core.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Inventory {

    private long id;
    private ObservableList<Order> currentOrders;
    private ObservableList<InventoryItem> inventory;
    private ObservableList<InventoryItem> products;

    private static Inventory instance;

    public static Inventory getInstance() {
        return instance == null ? instance = new Inventory() : instance;
    }

    public static Inventory getInstance(List<InventoryItem> inventory) {
        return instance == null ? instance = new Inventory(inventory) : instance;
    }

    public Inventory() {
        this.inventory = FXCollections.observableArrayList();
        this.currentOrders = FXCollections.observableArrayList();
        this.products = FXCollections.observableArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
            id = Long.valueOf(reader.readLine().split(":")[1]);
        } catch(Exception e) {
            id = 1;
        }
    }

    public Inventory(List<InventoryItem> inventory) {
        this.inventory = FXCollections.observableArrayList(inventory);
        this.currentOrders = FXCollections.observableArrayList();
        this.products = FXCollections.observableArrayList();
        reCalculate();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
            id = Long.valueOf(reader.readLine().split(":")[1]);
        } catch(Exception e) {
            id = 1;
        }
    }

    public void addProduct(InventoryItem item) { products.add(item); }

    public void addItem(InventoryItem item) {
        inventory.add(item);
    }

    public void removeItem(InventoryItem item) {
        inventory.remove(item);
    }

    public boolean removeQuantities(List<InventoryItem> items) {
        for (InventoryItem item : items) {
            for (int i = 0; i < inventory.size(); i++) {
                InventoryItem invItem = inventory.get(i);
                if(invItem.getPartNumber().equalsIgnoreCase(item.getPartNumber())) {
                    if(invItem.getQuantityInHouse() < item.getQuantityInUse()) {
                        invItem.setQuantityInHouse(invItem.getQuantityInHouse() - item.getQuantityNeeded());
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean addItemsInUse(List<InventoryItem> items) {
        for (InventoryItem item : items) {
            for (int i = 0; i < inventory.size(); i++) {
                InventoryItem invItem = inventory.get(i);
                if(invItem.getPartNumber().equalsIgnoreCase(item.getPartNumber())) {
                    invItem.setQuantityInUse(item.getQuantityNeeded());
                }
            }
        }
        return true;
    }

    public void addOrder(Order order) { currentOrders.add(order);}

    public void removeOrder(Order order) { currentOrders.remove(order); }

    public ObservableList<InventoryItem> getInventory() {
        return inventory;
    }

    public ObservableList<InventoryItem> getProducts() {
        return products;
    }

    public ObservableList<Order> getCurrentOrders() {
        return currentOrders;
    }

    public void reCalculate() {
        System.out.println("Recalculating...");
        this.inventory.forEach(item -> {
            int currentQuantity = item.getQuantityInHouse();
            Inventory.getInstance().currentOrders.forEach(order -> {
                order.getMaterials().forEach(material -> {
                    if(material.getPartNumber().equalsIgnoreCase(item.getPartNumber())) {
                        item.setQuantityInHouse(item.getQuantityInHouse() + material.getQuantityNeeded());
                    }
                });
            });
            if(currentQuantity < item.getMinQty()) {
                item.setQuantityNeeded(item.getMinQty() - currentQuantity);
            } else {
                item.setQuantityNeeded(0);
            }
        });
    }

    public long getCurrentId() {
        return id;
    }

    public String createOrderId() {
        String idString = "";
        for(int zeros = 0; zeros < 8 - String.valueOf(id).length(); zeros++) {
            idString += "0";
        }
        idString += id;
        id++;
        return "O-" + idString;
    }

}
