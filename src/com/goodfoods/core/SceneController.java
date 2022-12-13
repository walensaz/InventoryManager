package com.goodfoods.core;

import com.goodfoods.core.objects.Inventory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class SceneController {

    private Scene scene;
    private Stage stage;

    private static SceneController instance;

    private SceneController(Stage stage) {
        this.stage = stage;
        Pane root = new Pane();
        scene = new Scene(root, 800,800);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> onCloseEvent());
    }

    // static method to create instance of Singleton class
    public static SceneController getInstance()
    {
        if (instance == null)
            instance = new SceneController(null);

        return instance;
    }

    public static SceneController getInstance(Stage stage)
    {
        if (instance == null)
            instance = new SceneController(stage);

        return instance;
    }

    public void changeToInventoryScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scenes/inventory.fxml"));
            stage.setTitle("Inventory Manager");
            stage.setScene(new Scene(root, 1920, 900));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void onCloseEvent() {
        try {
            File file = new File("inventory.txt");
            PrintWriter writer = new PrintWriter(file);
            Inventory.getInstance().getInventory().forEach(item -> {
                writer.println(item.toString());
                writer.flush();
            });
        } catch(IOException e) {
            e.printStackTrace();
        }
        try {
            File file = new File("orders.txt");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            PrintWriter writer = new PrintWriter(file);
            Inventory.getInstance().getCurrentOrders().forEach(order -> {
                String json = gson.toJson(order);
                writer.println(json);
                writer.flush();
            });
        } catch(IOException e) {
            e.printStackTrace();
        }
        try {
            File file = new File("config.txt");
            PrintWriter writer = new PrintWriter(file);
            writer.println("orderid:" + Inventory.getInstance().getCurrentId());
            writer.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
        Launcher.executor.shutdown();
    }
}