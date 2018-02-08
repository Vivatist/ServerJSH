package serverjsh.DAO.Interfaces;

import serverjsh.DAO.Entity.User;

import java.sql.SQLException;
import java.util.List;

public interface IUser {

    //create
    void add(User user) throws SQLException;

    //read
    List<User> getAll() throws SQLException;

    User getById(int id);

    //update
    void update(User user);

    //delete
    void remove(User user);

}
