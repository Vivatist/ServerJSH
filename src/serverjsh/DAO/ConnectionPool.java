package serverjsh.DAO;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import serverjsh.DAO.Interfaces.IConnection;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool implements IConnection{

    private static final Logger log = Logger.getLogger(ConnectionPool.class);

    private ComboPooledDataSource cpds = new ComboPooledDataSource();

    private static ConnectionPool ourInstance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return ourInstance;
    }

    private ConnectionPool() {
        try {
            cpds.setDriverClass("org.h2.Driver");
            cpds.setJdbcUrl("jdbc:h2:./jsh_DB");
            cpds.setUser("pi");
            cpds.setPassword("238938");

            cpds.setMaxStatements(180);
            cpds.setMaxStatementsPerConnection(180);
            cpds.setMinPoolSize(50);
            cpds.setAcquireIncrement(10);
            cpds.setMaxPoolSize(60);
            cpds.setMaxIdleTime(30);
            log.info("Connection pool is created");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {

        Connection connection = cpds.getConnection();
        return connection;
    }
}
