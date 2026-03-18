package controller.admin;

import data.dao.AccountDAO;
import data.impl.AccountImpl;
import data.model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/accounts")
public class ListAccountsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        AccountDAO accountDao = new AccountImpl();
        try {
            List<Account> accountList = accountDao.findAll();
            request.setAttribute("accountList", accountList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }
        
        request.setAttribute("adminPage", "accounts");
        request.getRequestDispatcher("/views/admin/accounts.jsp").forward(request, response);
    }
}