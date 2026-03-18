package data.model;

import java.math.BigDecimal;

public class Account {

    private int accountId;
    private String name;
    private BigDecimal balance;
    private int userId;

    public Account() {
    }

    public Account(int accountId, String name, BigDecimal balance, int userId) {
        this.accountId = accountId;
        this.name = name;
        this.balance = balance;
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
