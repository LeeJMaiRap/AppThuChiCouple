package data.dao;

import data.model.Category;
import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {
    boolean insert(Category category) throws SQLException;
    boolean update(Category category) throws SQLException;
    boolean delete(int id) throws SQLException;
    List<Category> findByUserId(int userId) throws SQLException;
    void addDefaultCategoriesForUser(int userId) throws SQLException;
    Category findByNameAndUserId(String name, int userId) throws SQLException;
    List<Category> findAll() throws SQLException;
    Category findById(int categoryId) throws SQLException;
}