package controller.admin;

import data.dao.CategoryDAO;
import data.impl.CategoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/categories/delete")
public class AdminDeleteCategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int categoryId = Integer.parseInt(request.getParameter("id"));
        CategoryDAO categoryDao = new CategoryImpl();
        try {
            categoryDao.delete(categoryId);
            request.getSession().setAttribute("message", "Xóa danh mục thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("message", "Lỗi: Không thể xóa danh mục.");
        }
        
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }
}