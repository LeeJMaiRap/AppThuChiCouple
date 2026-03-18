<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="sidebar d-flex flex-column flex-shrink-0 p-3 bg-dark text-white shadow-sm">
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
        <i class="bi bi-shield-lock-fill fs-4 me-2"></i>
        <span class="fs-4">Admin Panel</span>
    </a>
    <hr>
    <ul class="nav nav-pills flex-column mb-auto">
        <li class="nav-item">
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link ${adminPage == 'dashboard' ? 'active' : 'text-white'}" aria-current="page">
                <i class="bi bi-speedometer2 me-2"></i> Trang Chủ
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/users" class="nav-link ${adminPage == 'users' ? 'active' : 'text-white'}">
                <i class="bi bi-people-fill me-2"></i> Quản lý Người dùng
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/categories" class="nav-link ${adminPage == 'categories' ? 'active' : 'text-white'}">
                <i class="bi bi-tags-fill me-2"></i> Quản lý Danh mục
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/admin/accounts" class="nav-link ${adminPage == 'accounts' ? 'active' : 'text-white'}">
                <i class="bi bi-credit-card-2-front-fill me-2"></i> Quản lý Tài khoản
            </a>
        </li>
    </ul>
    <hr>
    <div class="dropdown">
        <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" data-bs-toggle="dropdown">
            <i class="bi bi-person-circle fs-4 me-2"></i>
            <strong>${sessionScope.user.fullName}</strong>
        </a>
        <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/home"><i class="bi bi-house-door me-2"></i>Về trang người dùng</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-2"></i>Đăng xuất</a></li>
        </ul>
    </div>
</div>