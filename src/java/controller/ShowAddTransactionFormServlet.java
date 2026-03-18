package controller;

import data.dao.AccountDAO;
import data.dao.CategoryDAO;
import data.impl.AccountImpl;
import data.impl.CategoryImpl;
import data.model.Account;
import data.model.Category;
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

@WebServlet("/add-transaction")
public class ShowAddTransactionFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        CategoryDAO categoryDAO = new CategoryImpl();
        AccountDAO accountDAO = new AccountImpl();
        try {
            List<Category> categories = categoryDAO.findByUserId(user.getUserId());
            List<Account> accounts = accountDAO.findByUserId(user.getUserId());
            request.setAttribute("categories", categories);
            request.setAttribute("accounts", accounts);
        } catch (SQLException e) {
            throw new ServletException("Database error loading form data", e);
        }
        request.getRequestDispatcher("/views/add-transaction.jsp").forward(request, response);
    }
}