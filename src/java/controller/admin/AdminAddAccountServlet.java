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

@WebServlet("/admin/accounts/add")
public class AdminAddAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        UserDAO userDao = new UserImpl();
        try {
            List<User> userList = userDao.findAll();
            request.setAttribute("userList", userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("adminPage", "accounts");
        request.getRequestDispatcher("/views/admin/add-account.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String name = request.getParameter("name");
        BigDecimal balance = new BigDecimal(request.getParameter("balance"));
        int userId = Integer.parseInt(request.getParameter("userId"));

        AccountDAO accountDao = new AccountImpl();
        try {
            Account newAccount = new Account(0, name, balance, userId);
            accountDao.insert(newAccount);
            request.getSession().setAttribute("message", "Thêm tài khoản mới thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Lỗi: Không thể thêm tài khoản mới.");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/accounts");
    }
}