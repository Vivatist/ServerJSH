package serverjsh.DAO.Service;

import org.apache.log4j.Logger;
import serverjsh.DAO.ConnectionPool;
import serverjsh.DAO.Entity.User;
import serverjsh.DAO.Interfaces.IUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService  implements IUser {



    @Override
    public void add(User user) throws SQLException {

        Connection connection = ConnectionPool.getInstance().getConnection();

        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO users (id, login, password, groupId) VALUES (?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getGroupId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }


    }

    @Override
    public List<User> getAll() throws SQLException {

        Connection connection = ConnectionPool.getInstance().getConnection();
        List<User> userList = new ArrayList<>();

        String sql = "SELECT * FROM users";

        Statement statement = null;
        ResultSet resultSet = null;

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getInt("ID"));
                user.setLogin(resultSet.getString("LOGIN"));
                user.setPassword(resultSet.getString("PASSWORD"));
                user.setGroupId(resultSet.getInt("GROUPID"));

                userList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        return userList;
    }


    @Override
    public User getById(int id) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        User user = new User();

        String sql = "SELECT id, login, password, groupId FROM users WHERE id = ?";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            resultSet.next(); //TODO убедится в необходимости, в примере почему-то не было
            user.setId(resultSet.getInt("id"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setGroupId(resultSet.getInt("groupId"));

            //preparedStatement.executeUpdate(); //TODO убедится в необходимости, в примере зачем-то было

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }

        }

        return user;
    }

    @Override
    public void update(User user) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE users SET login=?, password=?, groupId=? WHERE id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getGroupId());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Override
    public void remove(User user) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM users WHERE id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }
}
