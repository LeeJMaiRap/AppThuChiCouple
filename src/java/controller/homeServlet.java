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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/home")
public class homeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String message = (String) session.getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }
        
        TransactionDAO transactionDao = new TransactionImpl();
        AccountDAO accountDao = new AccountImpl();
        CategoryDAO categoryDao = new CategoryImpl();
        
        try {
            List<Transaction> transactionList = transactionDao.findRecentByUserId(user.getUserId(), 10);
            BigDecimal totalBalance = accountDao.getTotalBalanceByUserId(user.getUserId());
            BigDecimal monthlyIncome = transactionDao.getMonthlyTotal(user.getUserId(), "income");
            BigDecimal monthlyExpense = transactionDao.getMonthlyTotal(user.getUserId(), "expense");

            List<Account> accounts = accountDao.findByUserId(user.getUserId());
            List<Category> categories = categoryDao.findByUserId(user.getUserId());
            
            request.setAttribute("transactionList", transactionList);
            request.setAttribute("totalBalance", totalBalance);
            request.setAttribute("monthlyIncome", monthlyIncome);
            request.setAttribute("monthlyExpense", monthlyExpense);
            
            request.setAttribute("accounts", accounts);
            request.setAttribute("categories", categories);
            
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw new ServletException("Lỗi truy vấn cơ sở dữ liệu ở trang chủ", e);
        }
        
        request.getRequestDispatcher("/views/home.jsp").forward(request, response);
    }
}