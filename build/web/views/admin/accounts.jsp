<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="/inc/header.jsp" />

<div class="main-layout">
    <c:import url="/inc/_admin-sidebar.jsp" />
    <div class="content-wrapper">
        <main class="flex-grow-1 p-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h1>Quản lý Tài khoản</h1>
                <a href="${pageContext.request.contextPath}/admin/accounts/add" class="btn btn-primary">
                    <i class="bi bi-plus-circle-fill me-2"></i>Thêm tài khoản mới
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
                                    <th>Tên Tài khoản</th>
                                    <th class="text-end">Số dư</th>
                                    <th>User ID</th>
                                    <th class="text-end">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="acc" items="${accountList}">
                                    <tr>
                                        <td>${acc.accountId}</td>
                                        <td>${acc.name}</td>
                                        <td class="text-end">
                                            <fmt:formatNumber value="${acc.balance}" type="currency" currencySymbol="" groupingUsed="true" maxFractionDigits="0"/>₫
                                        </td>
                                        <td>${acc.userId}</td>
                                        <td class="text-end">
                                            <a href="${pageContext.request.contextPath}/admin/accounts/edit?id=${acc.accountId}" class="btn btn-sm btn-outline-primary"><i class="bi bi-pencil-square"></i> Sửa</a>
                                            <a href="${pageContext.request.contextPath}/admin/accounts/delete?id=${acc.accountId}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa tài khoản [${acc.name}] không?');"><i class="bi bi-trash3"></i> Xóa</a>
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