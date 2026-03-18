package controller;

import data.dao.AccountDAO;
import data.dao.CategoryDAO;
import data.dao.TransactionDAO;
import data.driver.MySQLDriver;
import data.impl.AccountImpl;
import data.impl.CategoryImpl;
import data.impl.TransactionImpl;
import data.model.Category;
import data.model.Transaction;
import data.model.User;
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

@WebServlet("/process-add-transaction")
public class AddTransactionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String dateStr = request.getParameter("transactionDate");
        if (dateStr == null || dateStr.isEmpty()) {
            session.setAttribute("message", "Lỗi: Ngày giao dịch không được để trống.");
            response.sendRedirect("home");
            return;
        }

        Connection conn = null;
        try {
            String type = request.getParameter("type");
            String categoryName = request.getParameter("categoryName");
            String note = request.getParameter("note");
            
            CategoryDAO categoryDao = new CategoryImpl();
            Category category = categoryDao.findByNameAndUserId(categoryName, user.getUserId());
            
            if (category == null) {
                session.setAttribute("message", "Lỗi: Không tìm thấy danh mục hợp lệ.");
                response.sendRedirect("home");
                return;
            }

            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
            Date transactionDate = Date.valueOf(dateStr);
            int accountId = Integer.parseInt(request.getParameter("accountId"));

            Transaction newTransaction = new Transaction();
            newTransaction.setUserId(user.getUserId());
            newTransaction.setAmount(amount);
            newTransaction.setTransactionDate(transactionDate);
            newTransaction.setType(type);
            newTransaction.setCategoryId(category.getCategoryId());
            newTransaction.setAccountId(accountId);
            newTransaction.setNote("Khác".equals(categoryName) ? note : categoryName);

            conn = MySQLDriver.getConnection();
            conn.setAutoCommit(false);

            TransactionDAO transactionDao = new TransactionImpl();
            AccountDAO accountDao = new AccountImpl();

            transactionDao.insert(newTransaction);
            
            BigDecimal amountToUpdate = type.equals("expense") ? amount.negate() : amount;
            accountDao.updateBalance(accountId, amountToUpdate);

            conn.commit();
            session.setAttribute("message", "Thêm giao dịch thành công!");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            session.setAttribute("message", "Lỗi: Không thể thêm giao dịch do lỗi CSDL.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            session.setAttribute("message", "Lỗi: Dữ liệu nhập vào không hợp lệ.");
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        response.sendRedirect("home");
    }
}