package serverjsh.DAO;

import serverjsh.Services.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    public static final String DB_URL = "jdbc:h2:./jsh_DB";
    public static final String DB_DRIVER = "org.h2.Driver";
    public static final String DB_USER = "pi";
    public static final String DB_PASSWORD = "238938";

    public Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            Log.out("Connection Ok", 3);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.out("Connection Error: " + e.toString(), 1);
        }
        return connection;
    }

}
