package data.dao;

import data.model.Account;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {
    boolean insert(Account account) throws SQLException;
    boolean update(Account account) throws SQLException;
    boolean delete(int id) throws SQLException;
    List<Account> findByUserId(int userId) throws SQLException;
    void addDefaultAccountForUser(int userId) throws SQLException;
    BigDecimal getTotalBalanceByUserId(int userId) throws SQLException;
    void updateBalance(int accountId, BigDecimal amount) throws SQLException;
    List<Account> findAll() throws SQLException;
    Account findById(int accountId) throws SQLException;
}