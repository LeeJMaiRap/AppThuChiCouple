<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:import url="/inc/header.jsp" />

<div class="main-layout">
    <c:import url="/inc/_sidebar.jsp" />

    <div class="content-wrapper">
        <main class="flex-grow-1 p-4">
            <c:if test="${not empty message}"><div class="alert alert-info alert-dismissible fade show" role="alert">${message}<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div></c:if>
            
            <div class="d-lg-none d-flex justify-content-between align-items-center mb-3">
                <h4 class="mb-0">Lịch sử Giao dịch</h4>
                <button class="btn btn-outline-secondary" type="button" onclick="toggleSidebar()"><i class="bi bi-list fs-4"></i></button>
            </div>
            <h2 class="mb-4 d-none d-lg-block">Lịch sử Giao dịch</h2>

            <div class="card shadow-sm mb-4">
                <div class="card-body">
                    <form action="history" method="get" class="row g-3 align-items-end">
                        <div class="col-md-3">
                            <label for="startDate" class="form-label">Từ ngày</label>
                            <input type="date" class="form-control" id="startDate" name="startDate" value="${filter_startDate}">
                        </div>
                        <div class="col-md-3">
                            <label for="endDate" class="form-label">Đến ngày</label>
                            <input type="date" class="form-control" id="endDate" name="endDate" value="${filter_endDate}">
                        </div>
                        <div class="col-md-4">
                            <label for="keyword" class="form-label">Ghi chú</label>
                            <input type="text" class="form-control" id="keyword" name="keyword" placeholder="Tìm theo ghi chú..." value="${filter_keyword}">
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary w-100">Lọc</button>
                        </div>
                        <div class="col-md-3">
                            <label for="accountId" class="form-label">Tài khoản</label>
                            <select name="accountId" id="accountId" class="form-select">
                                <option value="0">Tất cả tài khoản</option>
                                <c:forEach var="acc" items="${accounts}">
                                    <option value="${acc.accountId}" ${filter_accountId == acc.accountId ? 'selected' : ''}>${acc.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-5">
                            <label for="categoryId" class="form-label">Danh mục</label>
                            <select name="categoryId" id="categoryId" class="form-select">
                                <option value="0">Tất cả danh mục</option>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.categoryId}" ${filter_categoryId == cat.categoryId ? 'selected' : ''}>${cat.name} (${cat.type})</option>
                                </c:forEach>
                            </select>
                        </div>
                         <div class="col-md-2">
                            <a href="history" class="btn btn-outline-secondary w-100">Xóa lọc</a>
                        </div>
                    </form>
                </div>
            </div>

            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead>
                                <tr>
                                    <th><a href="history?sortField=transaction_date&sortOrder=${sortField == 'transaction_date' ? reverseSortOrder : 'DESC'}" class="text-decoration-none text-dark">Ngày <c:if test="${sortField == 'transaction_date'}"><i class="bi bi-arrow-${sortOrder == 'ASC' ? 'up' : 'down'}"></i></c:if></a></th>
                                    <th><a href="history?sortField=type&sortOrder=${sortField == 'type' ? reverseSortOrder : 'ASC'}" class="text-decoration-none text-dark">Loại <c:if test="${sortField == 'type'}"><i class="bi bi-arrow-${sortOrder == 'ASC' ? 'up' : 'down'}"></i></c:if></a></th>
                                    <th>Mô tả</th>
                                    <th>Tài khoản</th>
                                    <th class="text-end"><a href="history?sortField=amount&sortOrder=${sortField == 'amount' ? reverseSortOrder : 'DESC'}" class="text-decoration-none text-dark">Số tiền <c:if test="${sortField == 'amount'}"><i class="bi bi-arrow-${sortOrder == 'ASC' ? 'up' : 'down'}"></i></c:if></a></th>
                                    <th class="text-end">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="txn" items="${transactionList}">
                                    <tr>
                                        <td><fmt:formatDate value="${txn.transactionDate}" pattern="dd/MM/yyyy"/></td>
                                        <td>
                                            <c:if test="${txn.type == 'income'}"><span class="badge bg-success-subtle text-success-emphasis rounded-pill">Thu nhập</span></c:if>
                                            <c:if test="${txn.type == 'expense'}"><span class="badge bg-danger-subtle text-danger-emphasis rounded-pill">Chi tiêu</span></c:if>
                                        </td>
                                        <td>
                                            <strong>${txn.categoryName}</strong>
                                            <c:if test="${txn.note != txn.categoryName}"><br><small class="text-muted">${txn.note}</small></c:if>
                                        </td>
                                        <td>${txn.accountName}</td>
                                        <td class="text-end fw-bold ${txn.type == 'income' ? 'text-success' : 'text-danger'}">
                                            ${txn.type == 'income' ? '+' : '-'} <fmt:formatNumber value="${txn.amount}" type="currency" currencySymbol="" groupingUsed="true" maxFractionDigits="0"/>₫
                                        </td>
                                        <td class="text-end">
                                            <a href="edit-transaction?id=${txn.transactionId}" class="btn btn-sm btn-outline-primary"><i class="bi bi-pencil-square"></i></a>
                                            <a href="delete-transaction?id=${txn.transactionId}" class="btn btn-sm btn-outline-danger" onclick="return confirm('Bạn có chắc muốn xóa giao dịch này?');"><i class="bi bi-trash3"></i></a>
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