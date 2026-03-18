package controller;

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
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/set-new-password")
public class SetNewPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("is_verified_for_reset") == null) {
            response.sendRedirect("forgot-password");
            return;
        }
        request.getRequestDispatcher("/views/set-new-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("is_verified_for_reset") == null) {
            response.sendRedirect("forgot-password");
            return;
        }

        String email = (String) session.getAttribute("reset_email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
            request.getRequestDispatcher("/views/set-new-password.jsp").forward(request, response);
            return;
        }

        UserDAO userDao = new UserImpl();
        try {
            User user = userDao.findByEmail(email);
            
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            user.setPassword(hashedPassword);
            user.setResetToken(null);
            user.setResetTokenExpiresAt(null);
            userDao.update(user);
            
            session.removeAttribute("reset_email");
            session.removeAttribute("is_verified_for_reset");

            session.setAttribute("successMessage", "Mật khẩu của bạn đã được đặt lại thành công. Vui lòng đăng nhập.");
            response.sendRedirect("login");
            
        } catch (SQLException e) {
             e.printStackTrace();
             throw new ServletException("Lỗi CSDL", e);
        }
    }
}