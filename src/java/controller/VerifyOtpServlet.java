package controller;

import data.dao.UserDAO;
import data.impl.UserImpl;
import data.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/verify-otp")
public class VerifyOtpServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/verify-otp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("reset_email");
        String otp = request.getParameter("otp");
        
        UserDAO userDao = new UserImpl();
        try {
            User user = userDao.findByEmail(email);
            
            if (user != null && otp.equals(user.getResetToken()) && user.getResetTokenExpiresAt().after(new Timestamp(System.currentTimeMillis()))) {
                request.getSession().setAttribute("is_verified_for_reset", true);
                response.sendRedirect("set-new-password");
            } else {
                request.setAttribute("error", "Mã OTP không hợp lệ hoặc đã hết hạn.");
                request.getRequestDispatcher("/views/verify-otp.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Lỗi CSDL", e);
        }
    }
}