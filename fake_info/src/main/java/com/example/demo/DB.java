package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.demo.info.Info;

public class DB {

    protected Connection connection;

    /**
     * Opens a connection to the database
     */
    public DB() {
        String url = "jdbc:mysql://" + Info.host() + "/" + Info.dbName() + "?useUnicode=true&characterEncoding=utf8";

        try {
            connection = DriverManager.getConnection(
                    url,
                    Info.user(),
                    Info.password()
            );
        } catch (SQLException e) {
            System.out.println("Connection unsuccessful");
            throw new RuntimeException("Connection unsuccessful: " + e.getMessage());
        }
    }

    /**
     * Closes the connection to the database
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}