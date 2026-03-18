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

@WebServlet("/admin/categories/edit")
public class AdminEditCategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        CategoryDAO categoryDao = new CategoryImpl();
        UserDAO userDao = new UserImpl();
        try {
            Category category = categoryDao.findById(categoryId);
            List<User> userList = userDao.findAll(); // Lấy danh sách user để chọn
            
            request.setAttribute("categoryToEdit", category);
            request.setAttribute("userList", userList);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("adminPage", "categories");
        request.getRequestDispatcher("/views/admin/edit-category.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        String description = request.getParameter("description");
        int userId = Integer.parseInt(request.getParameter("userId"));
        
        CategoryDAO categoryDao = new CategoryImpl();
        try {
            Category category = new Category(categoryId, name, type, description, userId);
            categoryDao.update(category);
            request.getSession().setAttribute("message", "Cập nhật danh mục thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Lỗi: Không thể cập nhật danh mục.");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }
}