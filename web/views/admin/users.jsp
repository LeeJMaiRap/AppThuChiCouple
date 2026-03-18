<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/inc/header.jsp" />

<div class="main-layout">
    <c:import url="/inc/_admin-sidebar.jsp" />
    <div class="content-wrapper">
        <main class="flex-grow-1 p-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h1>Quản lý Người dùng</h1>
                <a href="${pageContext.request.contextPath}/admin/users/add" class="btn btn-primary">
                    <i class="bi bi-plus-circle-fill me-2"></i>Thêm người dùng mới
                </a>
            </div>

            <c:if test="${not empty message}"><div class="alert alert-info">${message}</div></c:if>

            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead>
                                <tr class="table-light">
                                    <th>ID</th>
                                    <th>Tên đăng nhập</th>
                                    <th>Họ và Tên</th>
                                    <th>Email</th>
                                    <th>Quyền</th>
                                    <th class="text-end">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${userList}">
                                    <tr>
                                        <td>${user.userId}</td>
                                        <td>${user.username}</td>
                                        <td>${user.fullName}</td>
                                        <td>${user.email}</td>
                                        <td>
                                            <c:if test="${user.role == 'admin'}"><span class="badge bg-danger">Admin</span></c:if>
                                            <c:if test="${user.role == 'user'}"><span class="badge bg-secondary">User</span></c:if>
                                        </td>
                                        <td class="text-end">
                                            <a href="${pageContext.request.contextPath}/admin/users/edit?id=${user.userId}" class="btn btn-sm btn-outline-primary">
                                                <i class="bi bi-pencil-square"></i> Sửa
                                            </a>
                                            <%-- Kích hoạt nút Xóa với cảnh báo JavaScript --%>
                                            <a href="${pageContext.request.contextPath}/admin/users/delete?id=${user.userId}" class="btn btn-sm btn-outline-danger" 
                                               onclick="return confirm('Bạn có chắc chắn muốn xóa người dùng [${user.username}] không?');">
                                                <i class="bi bi-trash3"></i> Xóa
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<c:import url="/inc/footer.jsp" />