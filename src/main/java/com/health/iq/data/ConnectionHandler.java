package com.health.iq.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by lsmon on 11/2/16.
 */
public class ConnectionHandler {
    private static Connection connection = null;

    public static Connection getConnection(){
        if (connection == null) new ConnectionHandler();
        return connection;
    }

    private ConnectionHandler(){
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:blood-sugar.db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(){
        if(connection != null)
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static void close(PreparedStatement preparedStatement) {
        if (preparedStatement != null) try {
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
