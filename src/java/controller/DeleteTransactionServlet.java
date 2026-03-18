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
import java.sql.SQLException;

@WebServlet("/delete-transaction")
public class DeleteTransactionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Connection conn = null;
        String idStr = request.getParameter("id");

        if (idStr != null) {
            try {
                int transactionId = Integer.parseInt(idStr);

                conn = MySQLDriver.getConnection();
                conn.setAutoCommit(false);

                TransactionDAO transactionDao = new TransactionImpl();
                AccountDAO accountDao = new AccountImpl();

                Transaction transactionToDelete = transactionDao.findById(transactionId);
                if (transactionToDelete == null) {
                    throw new SQLException("Không tìm thấy giao dịch để xóa.");
                }

                BigDecimal amountToRestore = transactionToDelete.getType().equals("expense")
                                             ? transactionToDelete.getAmount()
                                             : transactionToDelete.getAmount().negate();
                accountDao.updateBalance(transactionToDelete.getAccountId(), amountToRestore);

                transactionDao.delete(transactionId);

                conn.commit();
                session.setAttribute("message", "Xóa giao dịch thành công!");

            } catch (NumberFormatException | SQLException e) {
                if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
                session.setAttribute("message", "Lỗi: Không thể xóa giao dịch.");
                e.printStackTrace();
            } finally {
                if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
        response.sendRedirect("history");
    }
}