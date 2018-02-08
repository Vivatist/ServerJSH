package serverjsh.DAO;

import serverjsh.Services.Log;

import java.sql.*;

public class Base {

    private static String mUrl;
    private static String mUser;
    private static String mPassword;

    static Statement mStatement;

    public static void Connect (String url, String user, String password) {

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.out(e.toString(), 1);
        }

        mUrl = url;
        mUser = user;
        mPassword = password;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(mUrl, mUser, mPassword);
            //connection = DriverManager.getConnection("jdbc:h2:./jsh_DB", "pi", "238938");
            mStatement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.out(e.toString(), 1);
        }
    }


    public static boolean Execute(String SqlString) throws SQLException {

        return mStatement.execute(SqlString);
    }

    public static ResultSet ExecuteQuery(String SqlString) throws SQLException {

        return mStatement.executeQuery(SqlString);
    }


}
