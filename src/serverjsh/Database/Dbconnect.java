package serverjsh.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Dbconnect {


    public static void CreateDB() {

        try {

            Class.forName("org.h2.Driver");

            Connection conn = DriverManager.getConnection("jdbc:h2:./jsh_DB", "pi", "238938");

            Statement st = conn.createStatement();

            st.execute(" CREATE TABLE Student (Code INTEGER NOT NULL, Name CHAR (30) NOT NULL , Address CHAR (50),Mark DECIMAL)");

            System.out.println("Table create successfully...");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}
