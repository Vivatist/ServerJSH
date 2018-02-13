package serverjsh.DAO.Service;

import serverjsh.DAO.ConnectionPool;
import serverjsh.DAO.Interfaces.IBasicSql;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class BasicSql implements IBasicSql {

    @Override
    public List<String> Exec(String sql) throws SQLException {

        Connection connection = ConnectionPool.getInstance().getConnection();

        List<String> resultList = new ArrayList<>();

        Statement statement = null;

        ResultSet resultSet = null;

        try {


            statement = connection.createStatement();
            if (statement.execute(sql)) {
                resultSet = statement.getResultSet();
                // Количество колонок в результирующем запросе
                int columns = resultSet.getMetaData().getColumnCount();
                String line;
                // Перебор строк с данными
                while (resultSet.next()) {
                    line = "";
                    for (int i = 1; i <= columns; i++) {
                        line += (resultSet.getString(i) + "\t");
                    }
                    resultList.add(line);
                }
            } else {
                resultList.add("SQL Ok");
            }


        } catch (SQLException e) {
            e.printStackTrace();
            //TODO сделать нормальную обработку исключение, отправить наверх свое исключение
            resultList.add(e.toString());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }

        }
        return resultList;
    }
}
