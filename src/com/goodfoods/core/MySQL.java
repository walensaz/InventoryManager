package com.goodfoods.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQL {

    private Connection connection;

    private static MySQL instance;

    private MySQL(String host, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql:" + host, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery("select * from emp");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MySQL getInstance() {
        instance = instance == null ? instance = new MySQL(Config.DATABASE_HOST_ADDRESS, Config.DATABASE_USERNAME, Config.DATABASE_PASSWORD) : instance;
        return instance;
    }

}
