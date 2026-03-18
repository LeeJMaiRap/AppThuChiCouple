<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="sidebar d-flex flex-column flex-shrink-0 p-3 bg-white shadow-sm">

    <a href="${pageContext.request.contextPath}/home" class="d-flex align-items-center justify-content-center mb-3 link-dark text-decoration-none">
        <img src="${pageContext.request.contextPath}/assets/images/thu-chi-main-logo.png" alt="Logo" height="60" class="me-2">
    </a>

    <ul class="nav nav-pills flex-column mb-auto">
        <li class="nav-item">
            <a href="${pageContext.request.contextPath}/home" class="nav-link ${empty activePage ? 'active' : 'link-dark'}" aria-current="page">
                <i class="bi bi-house-door me-2"></i> Trang chủ
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/history" class="nav-link ${activePage == 'history' ? 'active' : 'link-dark'}">
                <i class="bi bi-table me-2"></i> Lịch sử Giao dịch
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/statistics" class="nav-link ${activePage == 'statistics' ? 'active' : 'link-dark'}">
                <i class="bi bi-pie-chart me-2"></i>
                Thống kê
            </a>
        </li>
    </ul>

    <hr>
    <div class="dropdown">
        <a href="#" class="d-flex align-items-center link-dark text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="bi bi-person-circle fs-4 me-2"></i>
            <strong>${sessionScope.user.fullName}</strong>
        </a>
        <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
            <c:if test="${sessionScope.user.role == 'admin'}">
                <li class="nav-item mt-3">
                    <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link text-bg-primary">
                        <i class="bi bi-shield-lock-fill me-2"></i>
                        <strong>Vào trang Quản trị</strong>
                    </a>
                </li>
            </c:if>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile"><i class="bi bi-person-vcard me-2"></i>Thông tin cá nhân</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/change-password"><i class="bi bi-key me-2"></i>Đổi mật khẩu</a></li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><i class="bi bi-box-arrow-right me-2"></i>Đăng xuất</a></li>
        </ul>
    </div>
</div>