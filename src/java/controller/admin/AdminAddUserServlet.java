package controller.admin;

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
import java.io.IOException;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/admin/users/add")
public class AdminAddUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("adminPage", "users");
        request.getRequestDispatcher("/views/admin/add-user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        UserDAO userDao = new UserImpl();
        try {
            if (userDao.findByUsername(username) != null) {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
                request.getRequestDispatcher("/views/admin/add-user.jsp").forward(request, response);
                return;
            }

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setRole(role);

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            newUser.setPassword(hashedPassword);

            User createdUser = userDao.insert(newUser);

            if (createdUser != null && createdUser.getUserId() > 0) {
                CategoryDAO categoryDao = new CategoryImpl();
                categoryDao.addDefaultCategoriesForUser(createdUser.getUserId());

                AccountDAO accountDao = new AccountImpl();
                accountDao.addDefaultAccountForUser(createdUser.getUserId());

                request.getSession().setAttribute("message", "Thêm người dùng mới thành công!");
            } else {
                request.getSession().setAttribute("message", "Lỗi: Không thể thêm người dùng mới.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Lỗi: Không thể thêm người dùng mới.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}
