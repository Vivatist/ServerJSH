package serverjsh.DAO.Interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface IConnection {

    Connection getConnection() throws SQLException;
}
