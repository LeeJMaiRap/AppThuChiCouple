package controller;

import data.dao.AccountDAO;
import data.dao.TransactionDAO;
import data.driver.MySQLDriver;
import data.impl.AccountImpl;
import data.impl.TransactionImpl;
import data.model.Transaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/process-edit-transaction")
public class EditTransactionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Connection conn = null;

        try {
            int transactionId = Integer.parseInt(request.getParameter("transactionId"));
            BigDecimal newAmount = new BigDecimal(request.getParameter("amount"));
            Date newTransactionDate = Date.valueOf(request.getParameter("transactionDate"));
            String newType = request.getParameter("type");
            int newCategoryId = Integer.parseInt(request.getParameter("categoryId"));
            int newAccountId = Integer.parseInt(request.getParameter("accountId"));
            String newNote = request.getParameter("note");

            conn = MySQLDriver.getConnection();
            conn.setAutoCommit(false);

            TransactionDAO transactionDao = new TransactionImpl();
            AccountDAO accountDao = new AccountImpl();

            Transaction oldTransaction = transactionDao.findById(transactionId);
            if (oldTransaction == null) {
                throw new SQLException("Không tìm thấy giao dịch để cập nhật.");
            }

            BigDecimal oldAmountForBalance = oldTransaction.getType().equals("expense") 
                                             ? oldTransaction.getAmount()
                                             : oldTransaction.getAmount().negate();
            accountDao.updateBalance(oldTransaction.getAccountId(), oldAmountForBalance);

            BigDecimal newAmountForBalance = newType.equals("expense") 
                                             ? newAmount.negate()
                                             : newAmount;
            accountDao.updateBalance(newAccountId, newAmountForBalance);
            
            Transaction transactionToUpdate = new Transaction();
            transactionToUpdate.setTransactionId(transactionId);
            transactionToUpdate.setAmount(newAmount);
            transactionToUpdate.setTransactionDate(newTransactionDate);
            transactionToUpdate.setType(newType);
            transactionToUpdate.setCategoryId(newCategoryId);
            transactionToUpdate.setAccountId(newAccountId);
            transactionToUpdate.setNote(newNote);
            
            transactionDao.update(transactionToUpdate);

            conn.commit();
            session.setAttribute("message", "Cập nhật giao dịch thành công!");

        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            session.setAttribute("message", "Lỗi: Không thể cập nhật giao dịch do lỗi CSDL.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            session.setAttribute("message", "Lỗi: Dữ liệu cập nhật không hợp lệ.");
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
        
        response.sendRedirect("history");
    }
}