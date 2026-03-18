<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/inc/header.jsp" />

<div class="main-layout">
    <c:import url="/inc/_admin-sidebar.jsp" />
    <div class="content-wrapper">
        <main class="flex-grow-1 p-4">
            <h1 class="mb-4">Sửa thông tin người dùng</h1>
            <div class="card shadow-sm">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/users/edit" method="post">
                        <input type="hidden" name="userId" value="${userToEdit.userId}">
                        
                        <div class="mb-3">
                            <label for="username" class="form-label">Tên đăng nhập (Không thể đổi)</label>
                            <input type="text" id="username" class="form-control" value="${userToEdit.username}" readonly>
                        </div>
                        
                        <div class="mb-3">
                            <label for="fullName" class="form-label">Họ và Tên</label>
                            <input type="text" id="fullName" name="fullName" class="form-control" value="${userToEdit.fullName}">
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" id="email" name="email" class="form-control" value="${userToEdit.email}">
                        </div>
                        
                        <div class="mb-3">
                            <label for="phone" class="form-label">Số điện thoại</label>
                            <input type="tel" id="phone" name="phone" class="form-control" value="${userToEdit.phone}">
                        </div>

                        <div class="mb-3">
                            <label for="role" class="form-label">Quyền</label>
                            <select id="role" name="role" class="form-select">
                                <option value="user" ${userToEdit.role == 'user' ? 'selected' : ''}>User</option>
                                <option value="admin" ${userToEdit.role == 'admin' ? 'selected' : ''}>Admin</option>
                            </select>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Hủy</a>
                    </form>
                </div>
            </div>
        </main>
    </div>
</div>

<c:import url="/inc/footer.jsp" />