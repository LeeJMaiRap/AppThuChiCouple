<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/inc/header.jsp" />

<div class="main-layout">
    <c:import url="/inc/_admin-sidebar.jsp" />
    <div class="content-wrapper">
        <main class="flex-grow-1 p-4">
            <h1 class="mb-4">Thêm người dùng mới</h1>
            <div class="card shadow-sm">
                <div class="card-body">
                    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
                    
                    <form action="${pageContext.request.contextPath}/admin/users/add" method="post">
                        <div class="mb-3">
                            <label for="username" class="form-label">Tên đăng nhập</label>
                            <input type="text" id="username" name="username" class="form-control" required>
                        </div>
                        
                         <div class="mb-3">
                            <label for="password" class="form-label">Mật khẩu</label>
                            <input type="password" id="password" name="password" class="form-control" required>
                        </div>

                        <div class="mb-3">
                            <label for="fullName" class="form-label">Họ và Tên</label>
                            <input type="text" id="fullName" name="fullName" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" id="email" name="email" class="form-control">
                        </div>
                        
                        <div class="mb-3">
                            <label for="phone" class="form-label">Số điện thoại</label>
                            <input type="tel" id="phone" name="phone" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label for="role" class="form-label">Quyền</label>
                            <select id="role" name="role" class="form-select">
                                <option value="user" selected>User</option>
                                <option value="admin">Admin</option>
                            </select>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Tạo người dùng</button>
                        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Hủy</a>
                    </form>
                </div>
            </div>
        </main>
    </div>
</div>

<c:import url="/inc/footer.jsp" />