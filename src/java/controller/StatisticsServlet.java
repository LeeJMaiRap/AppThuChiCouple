package controller;

import com.google.gson.Gson;
import data.dao.TransactionDAO;
import data.impl.TransactionImpl;
import data.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        TransactionDAO transactionDao = new TransactionImpl();
        
        try {
            List<Map<String, Object>> expenseData = transactionDao.getExpenseByCategory(user.getUserId());
            
            List<Map<String, Object>> summaryData = transactionDao.getMonthlySummary(user.getUserId());
            
            Gson gson = new Gson();
            String expenseDataJson = gson.toJson(expenseData);
            String summaryDataJson = gson.toJson(summaryData);
            
            request.setAttribute("expenseDataJson", expenseDataJson);
            request.setAttribute("summaryDataJson", summaryDataJson);
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Lỗi CSDL khi lấy dữ liệu thống kê", e);
        }
        
        request.setAttribute("activePage", "statistics");
        request.getRequestDispatcher("/views/statistics.jsp").forward(request, response);
    }
}