package org.example.mySqlConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/todolist";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("mySQL connection is connected");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
