package filter;

import data.dao.UserDAO;
import data.impl.UserImpl;
import data.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(urlPatterns = {"/home", "/add-transaction", "/edit-transaction", "/delete-transaction", "/process-add-transaction", "/process-edit-transaction"})
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        if (isLoggedIn) {
            chain.doFilter(request, response);
        } else {
            Cookie[] cookies = httpRequest.getCookies();
            Cookie userCookie = null;
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("userId".equals(c.getName())) {
                        userCookie = c;
                        break;
                    }
                }
            }

            if (userCookie != null) {
                try {
                    int userId = Integer.parseInt(userCookie.getValue());
                    UserDAO userDao = new UserImpl();
                    User user = userDao.findById(userId);
                    
                    if (user != null) {
                        session = httpRequest.getSession(true);
                        session.setAttribute("user", user);
                        chain.doFilter(request, response);
                    } else {
                        httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                    }
                } catch (NumberFormatException | SQLException e) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                }
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            }
        }
    }
}