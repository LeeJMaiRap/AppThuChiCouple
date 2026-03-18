<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/inc/header.jsp" />

<div class="main-layout">
    <c:import url="/inc/_admin-sidebar.jsp" />
    <div class="content-wrapper">
        <main class="flex-grow-1 p-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h1>Quản lý Danh mục</h1>
                <a href="${pageContext.request.contextPath}/admin/categories/add" class="btn btn-primary">
                    <i class="bi bi-plus-circle-fill me-2"></i>Thêm danh mục mới
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
                                    <th>Tên Danh mục</th>
                                    <th>Loại (Type)</th>
                                    <th>Mô tả</th>
                                    <th>User ID</th>
                                    <th class="text-end">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cat" items="${categoryList}">
                                    <tr>
                                        <td>${cat.categoryId}</td>
                                        <td>${cat.name}</td>
                                        <td>
                                            <c:if test="${cat.type == 'income'}"><span class="badge bg-success">Thu nhập</span></c:if>
                                            <c:if test="${cat.type == 'expense'}"><span class="badge bg-danger">Chi tiêu</span></c:if>
                                        </td>
                                        <td>${cat.description}</td>
                                        <td>${cat.userId}</td>
                                        <td class="text-end">
                                            <a href="${pageContext.request.contextPath}/admin/categories/edit?id=${cat.categoryId}" class="btn btn-sm btn-outline-primary"><i class="bi bi-pencil-square"></i> Sửa</a>
                                            <a href="${pageContext.request.contextPath}/admin/categories/delete?id=${cat.categoryId}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục [${cat.name}] không?');"><i class="bi bi-trash3"></i> Xóa</a>
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