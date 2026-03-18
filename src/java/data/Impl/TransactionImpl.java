package data.impl;

import data.dao.TransactionDAO;
import data.driver.MySQLDriver;
import data.model.Transaction;
import data.model.TransactionDetail;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionImpl implements TransactionDAO {

    private Transaction toTransaction(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getInt("transaction_id"),
                rs.getInt("user_id"),
                rs.getInt("account_id"),
                rs.getInt("category_id"),
                rs.getString("type"),
                rs.getBigDecimal("amount"),
                rs.getDate("transaction_date"),
                rs.getString("note"),
                rs.getTimestamp("created_at")
        );
    }

    @Override
    public boolean insert(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (user_id, account_id, category_id, type, amount, transaction_date, note) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setInt(2, transaction.getAccountId());
            stmt.setInt(3, transaction.getCategoryId());
            stmt.setString(4, transaction.getType());
            stmt.setBigDecimal(5, transaction.getAmount());
            stmt.setDate(6, transaction.getTransactionDate());
            stmt.setString(7, transaction.getNote());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Transaction transaction) throws SQLException {
        String sql = "UPDATE transactions SET account_id=?, category_id=?, type=?, amount=?, transaction_date=?, note=? WHERE transaction_id=?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getAccountId());
            stmt.setInt(2, transaction.getCategoryId());
            stmt.setString(3, transaction.getType());
            stmt.setBigDecimal(4, transaction.getAmount());
            stmt.setDate(5, transaction.getTransactionDate());
            stmt.setString(6, transaction.getNote());
            stmt.setInt(7, transaction.getTransactionId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE transaction_id=?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Transaction findById(int transactionId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return toTransaction(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Transaction> findByUserId(int userId) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(toTransaction(rs));
                }
            }
        }
        return list;
    }

    @Override
    public BigDecimal getMonthlyTotal(int userId, String type) throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM transactions "
                + "WHERE user_id = ? AND type = ? AND MONTH(transaction_date) = MONTH(CURDATE()) AND YEAR(transaction_date) = YEAR(CURDATE())";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, type);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal("total");
                    return (total == null) ? BigDecimal.ZERO : total;
                }
            }
        }
        return BigDecimal.ZERO;
    }

    @Override
    public List<Transaction> findRecentByUserId(int userId, int limit) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC, transaction_id DESC LIMIT ?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(toTransaction(rs));
                }
            }
        }
        return list;
    }

    @Override
    public List<TransactionDetail> findTransactionDetailsByUserId(int userId, String sortField, String sortOrder) throws SQLException {
        List<TransactionDetail> list = new ArrayList<>();
        String sql = "SELECT t.*, a.name as account_name, c.name as category_name "
                + "FROM transactions t "
                + "JOIN accounts a ON t.account_id = a.account_id "
                + "JOIN categories c ON t.category_id = c.category_id "
                + "WHERE t.user_id = ? "
                + "ORDER BY " + sortField + " " + sortOrder;

        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TransactionDetail detail = new TransactionDetail();
                    detail.setTransactionId(rs.getInt("transaction_id"));
                    detail.setUserId(rs.getInt("user_id"));
                    detail.setAccountId(rs.getInt("account_id"));
                    detail.setCategoryId(rs.getInt("category_id"));
                    detail.setType(rs.getString("type"));
                    detail.setAmount(rs.getBigDecimal("amount"));
                    detail.setTransactionDate(rs.getDate("transaction_date"));
                    detail.setNote(rs.getString("note"));
                    detail.setCreatedAt(rs.getTimestamp("created_at"));
                    detail.setAccountName(rs.getString("account_name"));
                    detail.setCategoryName(rs.getString("category_name"));
                    list.add(detail);
                }
            }
        }
        return list;
    }

    @Override
    public List<TransactionDetail> searchTransactions(int userId, String startDate, String endDate,
            int accountId, int categoryId, String keyword,
            String sortField, String sortOrder) throws SQLException {
        List<TransactionDetail> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT t.*, a.name as account_name, c.name as category_name "
                + "FROM transactions t "
                + "JOIN accounts a ON t.account_id = a.account_id "
                + "JOIN categories c ON t.category_id = c.category_id "
                + "WHERE t.user_id = ?"
        );
        params.add(userId);

        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND t.transaction_date >= ?");
            params.add(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND t.transaction_date <= ?");
            params.add(endDate);
        }
        if (accountId > 0) {
            sql.append(" AND t.account_id = ?");
            params.add(accountId);
        }
        if (categoryId > 0) {
            sql.append(" AND t.category_id = ?");
            params.add(categoryId);
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND t.note LIKE ?");
            params.add("%" + keyword.trim() + "%");
        }

        sql.append(" ORDER BY ").append(sortField).append(" ").append(sortOrder);

        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TransactionDetail detail = new TransactionDetail();
                    detail.setTransactionId(rs.getInt("transaction_id"));
                    detail.setUserId(rs.getInt("user_id"));
                    detail.setAccountId(rs.getInt("account_id"));
                    detail.setCategoryId(rs.getInt("category_id"));
                    detail.setType(rs.getString("type"));
                    detail.setAmount(rs.getBigDecimal("amount"));
                    detail.setTransactionDate(rs.getDate("transaction_date"));
                    detail.setNote(rs.getString("note"));
                    detail.setCreatedAt(rs.getTimestamp("created_at"));
                    detail.setAccountName(rs.getString("account_name"));
                    detail.setCategoryName(rs.getString("category_name"));
                    list.add(detail);
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getExpenseByCategory(int userId) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        // Lấy tổng chi tiêu theo danh mục trong tháng hiện tại
        String sql = "SELECT c.name, SUM(t.amount) as total "
                + "FROM transactions t "
                + "JOIN categories c ON t.category_id = c.category_id "
                + "WHERE t.user_id = ? AND t.type = 'expense' AND MONTH(t.transaction_date) = MONTH(CURDATE()) AND YEAR(t.transaction_date) = YEAR(CURDATE()) "
                + "GROUP BY c.name "
                + "ORDER BY total DESC";

        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("categoryName", rs.getString("name"));
                    row.put("totalAmount", rs.getBigDecimal("total"));
                    result.add(row);
                }
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getMonthlySummary(int userId) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();
        // Lấy tổng thu nhập và chi tiêu trong 6 tháng gần nhất
        String sql = "SELECT YEAR(transaction_date) as year, MONTH(transaction_date) as month, "
                + "SUM(CASE WHEN type = 'income' THEN amount ELSE 0 END) as total_income, "
                + "SUM(CASE WHEN type = 'expense' THEN amount ELSE 0 END) as total_expense "
                + "FROM transactions "
                + "WHERE user_id = ? AND transaction_date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) "
                + "GROUP BY YEAR(transaction_date), MONTH(transaction_date) "
                + "ORDER BY year, month";

        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("month", rs.getInt("month") + "/" + rs.getInt("year"));
                    row.put("totalIncome", rs.getBigDecimal("total_income"));
                    row.put("totalExpense", rs.getBigDecimal("total_expense"));
                    result.add(row);
                }
            }
        }
        return result;
    }
}
