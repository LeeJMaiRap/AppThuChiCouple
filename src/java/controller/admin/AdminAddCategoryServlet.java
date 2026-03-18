package controller.admin;

import data.dao.CategoryDAO;
import data.dao.UserDAO;
import data.impl.CategoryImpl;
import data.impl.UserImpl;
import data.model.Category;
import data.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/categories/add")
public class AdminAddCategoryServlet extends HttpServlet {

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
        
        request.setAttribute("adminPage", "categories");
        request.getRequestDispatcher("/views/admin/add-category.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        int userId = Integer.parseInt(request.getParameter("userId"));
        
        CategoryDAO categoryDao = new CategoryImpl();
        try {
            Category newCategory = new Category(0, name, type, description, userId);
            categoryDao.insert(newCategory);
            request.getSession().setAttribute("message", "Thêm danh mục mới thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Lỗi: Không thể thêm danh mục mới.");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }
}