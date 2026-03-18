package controller.admin;

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

@WebServlet("/admin/users/edit")
public class AdminEditUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        UserDAO userDao = new UserImpl();
        try {
            User user = userDao.findById(userId);
            request.setAttribute("userToEdit", user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("adminPage", "users");
        request.getRequestDispatcher("/views/admin/edit-user.jsp").forward(request, response);
    }

    // File: controller/admin/AdminEditUserServlet.java (Thêm logic cập nhật session)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int userId = Integer.parseInt(request.getParameter("userId"));
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        UserDAO userDao = new UserImpl();
        try {
            User userToUpdate = userDao.findById(userId);
            if (userToUpdate != null) {
                userToUpdate.setFullName(fullName);
                userToUpdate.setEmail(email);
                userToUpdate.setPhone(phone);
                userToUpdate.setRole(role);

                userDao.update(userToUpdate);
                request.getSession().setAttribute("message", "Cập nhật người dùng thành công!");

                User loggedInUser = (User) request.getSession().getAttribute("user");
                if (loggedInUser.getUserId() == userToUpdate.getUserId()) {
                    request.getSession().setAttribute("user", userToUpdate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Lỗi: Không thể cập nhật người dùng.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}
