package controller.admin;

import data.dao.CategoryDAO;
import data.impl.CategoryImpl;
import data.model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/categories")
public class ListCategoriesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        CategoryDAO categoryDao = new CategoryImpl();
        try {
            List<Category> categoryList = categoryDao.findAll();
            request.setAttribute("categoryList", categoryList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("adminPage", "categories");
        request.getRequestDispatcher("/views/admin/categories.jsp").forward(request, response);
    }
}