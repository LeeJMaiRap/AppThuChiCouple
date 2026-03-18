package data.impl;

import data.dao.CategoryDAO;
import data.driver.MySQLDriver;
import data.model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryImpl implements CategoryDAO {

    private Category toCategory(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("category_id"),
                rs.getString("name"),
                rs.getString("type"),
                rs.getString("description"),
                rs.getInt("user_id")
        );
    }

    @Override
    public boolean insert(Category category) throws SQLException {
        String sql = "INSERT INTO categories (name, type, description, user_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getType());
            stmt.setString(3, category.getDescription());
            stmt.setInt(4, category.getUserId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Category category) throws SQLException {
        String sql = "UPDATE categories SET name=?, type=?, description=? WHERE category_id=?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getType());
            stmt.setString(3, category.getDescription());
            stmt.setInt(4, category.getCategoryId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM categories WHERE category_id=?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Category> findByUserId(int userId) throws SQLException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE user_id = ?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(toCategory(rs));
                }
            }
        }
        return list;
    }

    @Override
    public void addDefaultCategoriesForUser(int userId) throws SQLException {
        String sql = "INSERT INTO categories (name, type, description, user_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Lương");
            stmt.setString(2, "income");
            stmt.setString(3, "Tiền lương hàng tháng");
            stmt.setInt(4, userId);
            stmt.addBatch();
            stmt.setString(1, "Thưởng");
            stmt.setString(2, "income");
            stmt.setString(3, "Các khoản thưởng");
            stmt.setInt(4, userId);
            stmt.addBatch();
            stmt.setString(1, "Ăn uống");
            stmt.setString(2, "expense");
            stmt.setString(3, "Chi phí ăn uống, nhà hàng");
            stmt.setInt(4, userId);
            stmt.addBatch();
            stmt.setString(1, "Mua sắm");
            stmt.setString(2, "expense");
            stmt.setString(3, "Mua sắm quần áo, vật dụng");
            stmt.setInt(4, userId);
            stmt.addBatch();
            stmt.setString(1, "Đi lại");
            stmt.setString(2, "expense");
            stmt.setString(3, "Chi phí xăng xe, đi lại");
            stmt.setInt(4, userId);
            stmt.addBatch();

            stmt.executeBatch();
        }
    }

    @Override
    public Category findByNameAndUserId(String name, int userId) throws SQLException {
        String sql = "SELECT * FROM categories WHERE name = ? AND user_id = ?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return toCategory(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM categories ORDER BY user_id, type";
        try (Connection conn = MySQLDriver.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categoryList.add(toCategory(rs));
            }
        }
        return categoryList;
    }

    @Override
    public Category findById(int categoryId) throws SQLException {
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return toCategory(rs);
                }
            }
        }
        return null;
    }
}
