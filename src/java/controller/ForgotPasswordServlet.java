package controller;

import data.dao.UserDAO;
import data.impl.UserImpl;
import data.model.User;
import data.utils.EmailUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDAO userDao = new UserImpl();

        try {
            User user = userDao.findByEmail(email);
            if (user == null) {
                request.setAttribute("error", "Không tìm thấy tài khoản nào với email này.");
                request.getRequestDispatcher("/views/forgot-password.jsp").forward(request, response);
                return;
            }

            Random random = new Random();
            int otp = 100000 + random.nextInt(900000);
            String token = String.valueOf(otp);

            long expiryTime = System.currentTimeMillis() + 10 * 60 * 1000;

            user.setResetToken(token);
            user.setResetTokenExpiresAt(new Timestamp(expiryTime));
            userDao.update(user);

            String emailBody = "Chào " + user.getFullName() + ",<br><br>"
                    + "Mã OTP để đặt lại mật khẩu của bạn là: <h2>" + token + "</h2>"
                    + "Mã sẽ hết hạn trong 10 phút. Nếu bạn không yêu cầu, vui lòng bỏ qua email này.";

            EmailUtil.sendEmail(user.getEmail(), "Mã OTP Đặt lại Mật khẩu", emailBody);

            request.getSession().setAttribute("reset_email", email);
            response.sendRedirect(request.getContextPath() + "/verify-otp");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi CSDL, vui lòng thử lại.");
            request.getRequestDispatcher("/views/forgot-password.jsp").forward(request, response);
        }
    }
}
