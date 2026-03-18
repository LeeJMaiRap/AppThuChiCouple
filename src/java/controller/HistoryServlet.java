package controller;

import data.dao.AccountDAO;
import data.dao.CategoryDAO;
import data.dao.TransactionDAO;
import data.impl.AccountImpl;
import data.impl.CategoryImpl;
import data.impl.TransactionImpl;
import data.model.Account;
import data.model.Category;
import data.model.TransactionDetail;
import data.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // --- XỬ LÝ SẮP XẾP ---
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");

        List<String> allowedSortFields = Arrays.asList("transaction_date", "type", "amount");
        if (sortField == null || !allowedSortFields.contains(sortField)) {
            sortField = "transaction_date";
        }
        if (sortOrder == null || (!sortOrder.equalsIgnoreCase("ASC") && !sortOrder.equalsIgnoreCase("DESC"))) {
            sortOrder = "DESC";
        }

        // --- XỬ LÝ LỌC ---
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String accountIdStr = request.getParameter("accountId");
        String categoryIdStr = request.getParameter("categoryId");
        String keyword = request.getParameter("keyword");

        int accountId = (accountIdStr == null || accountIdStr.isEmpty()) ? 0 : Integer.parseInt(accountIdStr);
        int categoryId = (categoryIdStr == null || categoryIdStr.isEmpty()) ? 0 : Integer.parseInt(categoryIdStr);
        
        // --- XỬ LÝ THÔNG BÁO ---
        String message = (String) session.getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message");
        }

        TransactionDAO transactionDao = new TransactionImpl();
        try {
            List<TransactionDetail> transactionList = transactionDao.searchTransactions(
                user.getUserId(), startDate, endDate, accountId, categoryId, keyword, sortField, sortOrder
            );
            request.setAttribute("transactionList", transactionList);

            AccountDAO accountDAO = new AccountImpl();
            CategoryDAO categoryDAO = new CategoryImpl();
            request.setAttribute("accounts", accountDAO.findByUserId(user.getUserId()));
            request.setAttribute("categories", categoryDAO.findByUserId(user.getUserId()));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi tải lịch sử giao dịch", e);
        }
        
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("reverseSortOrder", sortOrder.equals("ASC") ? "DESC" : "ASC");
        request.setAttribute("filter_startDate", startDate);
        request.setAttribute("filter_endDate", endDate);
        request.setAttribute("filter_accountId", accountId);
        request.setAttribute("filter_categoryId", categoryId);
        request.setAttribute("filter_keyword", keyword);

        request.setAttribute("activePage", "history"); 
        request.getRequestDispatcher("/views/history.jsp").forward(request, response);
    }
}