package controller.admin;

import data.dao.AccountDAO;
import data.impl.AccountImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/accounts/delete")
public class AdminDeleteAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int accountId = Integer.parseInt(request.getParameter("id"));
        AccountDAO accountDao = new AccountImpl();
        try {
            accountDao.delete(accountId);
            request.getSession().setAttribute("message", "Xóa tài khoản thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Lỗi: Không thể xóa tài khoản, có thể do vẫn còn các giao dịch liên quan.");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/accounts");
    }
}