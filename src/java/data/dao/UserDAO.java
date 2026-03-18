package data.dao;

import data.model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User insert(User user) throws SQLException;
    boolean update(User user) throws SQLException;
    boolean delete(int id) throws SQLException;
    User findById(int id) throws SQLException;
    User findByUsername(String username) throws SQLException;
    List<User> findAll() throws SQLException;
    User findByEmail(String email) throws SQLException;
    User findByResetToken(String token) throws SQLException;
}