package controller.admin;

import data.dao.AccountDAO;
import data.dao.UserDAO;
import data.impl.AccountImpl;
import data.impl.UserImpl;
import data.model.Account;
import data.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/accounts/edit")
public class AdminEditAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountId = Integer.parseInt(request.getParameter("id"));
        AccountDAO accountDao = new AccountImpl();
        UserDAO userDao = new UserImpl();
        try {
            Account account = accountDao.findById(accountId);
            List<User> userList = userDao.findAll();
            request.setAttribute("accountToEdit", account);
            request.setAttribute("userList", userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("adminPage", "accounts");
        request.getRequestDispatcher("/views/admin/edit-account.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int accountId = Integer.parseInt(request.getParameter("accountId"));
        String name = request.getParameter("name");
        BigDecimal balance = new BigDecimal(request.getParameter("balance"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        AccountDAO accountDao = new AccountImpl();
        try {
            Account account = new Account(accountId, name, balance, userId);
            accountDao.update(account);
            request.getSession().setAttribute("message", "Cập nhật tài khoản thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Lỗi: Không thể cập nhật tài khoản.");
        }
        response.sendRedirect(request.getContextPath() + "/admin/accounts");
    }
}