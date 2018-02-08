package serverjsh.DAO.Service;

import serverjsh.DAO.Entity.User;
import serverjsh.DAO.Interfaces.IUser;
import serverjsh.DAO.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService extends Util implements IUser {

    Connection connection = getConnection();

    @Override
    public void add(User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO users (id, login, password, groupId) VALUES (?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, user.getId());

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

        List<User> userList = new ArrayList<>();

        String sql = "SELECT * FROM users";

        Statement statement = null;

        try {

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getInt("ID"));
                user.setLogin(resultSet.getString("LOGIN"));
                user.setPassword(resultSet.getString(("PASSWORD")));
                user.setGroupId(resultSet.getInt("GROUPID"));

                userList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
    public User getById(int id) {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void remove(User user) {

    }
}
