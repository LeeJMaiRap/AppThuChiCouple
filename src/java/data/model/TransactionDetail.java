// File mới: data/model/TransactionDetail.java
package data.model;

public class TransactionDetail extends Transaction {
    private String accountName;
    private String categoryName;

    public TransactionDetail() {
        super();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}