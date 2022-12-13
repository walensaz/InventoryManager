package com.goodfoods.core;

import com.goodfoods.core.objects.Inventory;
import com.goodfoods.core.objects.InventoryItem;
import com.goodfoods.core.objects.Order;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Launcher extends Application {

    public static ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

    @Override
    public void start(Stage primaryStage) throws Exception{
        onStartup();
        SceneController.getInstance(primaryStage).changeToInventoryScene();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void onStartup() throws IOException {
        List<InventoryItem> items = new ArrayList<>();
        File inventoryFile = new File("inventory.txt");
        if(inventoryFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(inventoryFile));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] item = line.split(":");
                InventoryItem newItem = new InventoryItem(item[0], item[1], Integer.parseInt(item[2]), Integer.parseInt(item[3]), Integer.parseInt(item[4]), item[5], Integer.parseInt(item[6]), Float.parseFloat(item[7]), item[8]);
                items.add(newItem);
            }
            Inventory.getInstance(items);
            reader.close();
        }

        List<InventoryItem> orders = new ArrayList<>();
        File ordersFile = new File("orders.txt");
        Gson gson = new Gson();
        if(ordersFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(ordersFile));
            String temp = "";
            while((temp = reader.readLine()) != null) {
                Inventory.getInstance().addOrder(gson.fromJson(temp, Order.class));
            }
            System.out.println("Successfully added!");
        }
    }

}
