package controller;

import data.dao.AccountDAO;
import data.dao.CategoryDAO;
import data.dao.TransactionDAO;
import data.impl.AccountImpl;
import data.impl.CategoryImpl;
import data.impl.TransactionImpl;
import data.model.Account;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet("/edit-transaction")
public class ShowEditTransactionFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int transactionId = Integer.parseInt(request.getParameter("id"));

        TransactionDAO transactionDAO = new TransactionImpl();
        try {
            Transaction transaction = transactionDAO.findById(transactionId);
            if (transaction != null && transaction.getUserId() == user.getUserId()) {
                CategoryDAO categoryDAO = new CategoryImpl();
                AccountDAO accountDAO = new AccountImpl();
                List<Category> categories = categoryDAO.findByUserId(user.getUserId());
                List<Account> accounts = accountDAO.findByUserId(user.getUserId());
                
                request.setAttribute("transaction", transaction);
                request.setAttribute("categories", categories);
                request.setAttribute("accounts", accounts);
                request.setAttribute("activePage", "history"); 
                
                request.getRequestDispatcher("/views/edit-transaction.jsp").forward(request, response);
            } else {
                response.sendRedirect("home");
            }
        } catch (SQLException e) {
            throw new ServletException("Lỗi CSDL khi tải form sửa giao dịch", e);
        }
    }
}