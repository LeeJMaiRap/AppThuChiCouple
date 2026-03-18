package controller;

import data.dao.AccountDAO;
import data.dao.CategoryDAO;
import data.dao.UserDAO;
import data.impl.AccountImpl;
import data.impl.CategoryImpl;
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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO userDao = new UserImpl();
        try {
            if (userDao.findByUsername(username) != null) {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
                request.getRequestDispatcher("/views/register.jsp").forward(request, response);
                return;
            }

            User newUser = new User();
            newUser.setUsername(username);
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            newUser.setPassword(hashedPassword);

            newUser.setRole("user");

            User createdUser = userDao.insert(newUser);

            if (createdUser != null && createdUser.getUserId() > 0) {
                CategoryDAO categoryDao = new CategoryImpl();
                AccountDAO accountDao = new AccountImpl();

                categoryDao.addDefaultCategoriesForUser(createdUser.getUserId());
                accountDao.addDefaultAccountForUser(createdUser.getUserId());

                HttpSession session = request.getSession();
                session.setAttribute("user", createdUser);

                session.setAttribute("message", "Chào mừng bạn! Vui lòng cập nhật thông tin cá nhân.");
                response.sendRedirect("profile");
            } else {
                request.setAttribute("error", "Đã có lỗi xảy ra khi tạo tài khoản.");
                request.getRequestDispatcher("/views/register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error on registration", e);
        }
    }
}
