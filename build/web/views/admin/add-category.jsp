<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/inc/header.jsp" />

<div class="main-layout">
    <c:import url="/inc/_admin-sidebar.jsp" />
    <div class="content-wrapper">
        <main class="flex-grow-1 p-4">
            <h1 class="mb-4">Thêm Danh mục mới</h1>
            <div class="card shadow-sm">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/categories/add" method="post">
                        <div class="mb-3">
                            <label for="name" class="form-label">Tên Danh mục</label>
                            <input type="text" id="name" name="name" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label for="type" class="form-label">Loại</label>
                            <select id="type" name="type" class="form-select">
                                <option value="income">Thu nhập</option>
                                <option value="expense">Chi tiêu</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Mô tả</label>
                            <input type="text" id="description" name="description" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label for="userId" class="form-label">Thuộc về người dùng</label>
                            <select id="userId" name="userId" class="form-select">
                                <c:forEach var="user" items="${userList}">
                                    <option value="${user.userId}">${user.username} (ID: ${user.userId})</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Tạo danh mục</button>
                        <a href="${pageContext.request.contextPath}/admin/categories" class="btn btn-secondary">Hủy</a>
                    </form>
                </div>
            </div>
        </main>
    </div>
</div>

<c:import url="/inc/footer.jsp" />