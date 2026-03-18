package data.impl;

import data.dao.AccountDAO;
import data.driver.MySQLDriver;
import data.model.Account;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountImpl implements AccountDAO {

    private Account toAccount(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt("account_id"),
                rs.getString("name"),
                rs.getBigDecimal("balance"),
                rs.getInt("user_id")
        );
    }

    @Override
    public boolean insert(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (name, balance, user_id) VALUES (?, ?, ?)";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getName());
            stmt.setBigDecimal(2, account.getBalance());
            stmt.setInt(3, account.getUserId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Account account) throws SQLException {
        String sql = "UPDATE accounts SET name=?, balance=? WHERE account_id=?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getName());
            stmt.setBigDecimal(2, account.getBalance());
            stmt.setInt(3, account.getAccountId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_id=?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Account> findByUserId(int userId) throws SQLException {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(toAccount(rs));
                }
            }
        }
        return list;
    }

    @Override
    public void addDefaultAccountForUser(int userId) throws SQLException {
        String sql = "INSERT INTO accounts (name, balance, user_id) VALUES (?, ?, ?)";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Tiền mặt");
            stmt.setBigDecimal(2, new BigDecimal("0"));
            stmt.setInt(3, userId);
            stmt.addBatch();
            stmt.setString(1, "Tài khoản ngân hàng");
            stmt.setBigDecimal(2, new BigDecimal("0"));
            stmt.setInt(3, userId);
            stmt.addBatch();
            stmt.executeBatch();
        }
    }

    @Override
    public BigDecimal getTotalBalanceByUserId(int userId) throws SQLException {
        String sql = "SELECT SUM(balance) AS total FROM accounts WHERE user_id = ?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("total");
                }
            }
        }
        return BigDecimal.ZERO;
    }

    @Override
    public void updateBalance(int accountId, BigDecimal amount) throws SQLException {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, amount);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Account> findAll() throws SQLException {
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT * FROM accounts ORDER BY user_id";
        try (Connection conn = MySQLDriver.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                accountList.add(toAccount(rs));
            }
        }
        return accountList;
    }

    @Override
    public Account findById(int accountId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        try (Connection conn = MySQLDriver.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return toAccount(rs);
                }
            }
        }
        return null;
    }
}
