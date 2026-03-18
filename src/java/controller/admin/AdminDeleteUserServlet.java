package controller.admin;

import data.dao.UserDAO;
import data.impl.UserImpl;
import data.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/users/delete")
public class AdminDeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int userIdToDelete = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("user");

        // KIỂM TRA AN TOÀN: Admin không được tự xóa tài khoản của mình
        if (loggedInUser.getUserId() == userIdToDelete) {
            session.setAttribute("message", "Lỗi: Bạn không thể tự xóa tài khoản của mình.");
        } else {
            UserDAO userDao = new UserImpl();
            try {
                userDao.delete(userIdToDelete);
                session.setAttribute("message", "Xóa người dùng thành công!");
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("message", "Lỗi: Không thể xóa người dùng.");
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}