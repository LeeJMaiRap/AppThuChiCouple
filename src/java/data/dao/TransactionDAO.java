package data.dao;

import data.model.Transaction;
import data.model.TransactionDetail;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TransactionDAO {
    boolean insert(Transaction transaction) throws SQLException;
    boolean update(Transaction transaction) throws SQLException;
    boolean delete(int id) throws SQLException;
    Transaction findById(int transactionId) throws SQLException;
    List<Transaction> findByUserId(int userId) throws SQLException;
    BigDecimal getMonthlyTotal(int userId, String type) throws SQLException;
    List<Transaction> findRecentByUserId(int userId, int limit) throws SQLException;
    List<TransactionDetail> findTransactionDetailsByUserId(int userId, String sortField, String sortOrder) throws SQLException;
    List<TransactionDetail> searchTransactions(int userId, String startDate, String endDate, 
                                           int accountId, int categoryId, String keyword, 
                                           String sortField, String sortOrder) throws SQLException;
    List<Map<String, Object>> getExpenseByCategory(int userId) throws SQLException;
    List<Map<String, Object>> getMonthlySummary(int userId) throws SQLException;
}