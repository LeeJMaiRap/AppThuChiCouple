<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<main class="flex-grow-1 p-4">
    
    <div class="d-lg-none d-flex justify-content-between align-items-center mb-3">
        <a href="${pageContext.request.contextPath}/home" class="d-flex align-items-center justify-content-center mb-3 link-dark text-decoration-none">
            <img src="${pageContext.request.contextPath}/assets/images/thu-chi-main-logo.png" alt="Logo" height="60" class="me-2">
        </a>

        <h4 class="mb-0"></h4>
        <button class="btn btn-outline-secondary" type="button" onclick="toggleSidebar()">
            <i class="bi bi-list fs-4"></i>
        </button>
    </div>
<!--    <div class="row justify-content-center mb-4">
        <div class="col-lg-8">
            <div class="input-group input-group-lg shadow-sm">
                <input type="text" class="form-control" placeholder="Tìm kiếm giao dịch">
                <button class="btn btn-primary" type="button"><i class="bi bi-search"></i></button>
            </div>
        </div>
    </div>-->

    <%-- ... Phần hiển thị Thu nhập, Chi tiêu, và 10 giao dịch gần nhất ... --%>
    <c:if test="${not empty message}"><div class="alert alert-info alert-dismissible fade show" role="alert">${message}<button type="button" class="btn-close" data-bs-dismiss="alert"></button></div></c:if>
        <div class="row">
            <div class="col-lg-4 col-md-6 mb-4"><div class="card text-white bg-primary shadow-sm h-100"><div class="card-body"><h5 class="card-title"><i class="bi bi-wallet2"></i> Tổng số dư</h5><p class="card-text fs-2 fw-bold"><fmt:formatNumber value="${totalBalance}" type="currency" currencySymbol="" groupingUsed="true" maxFractionDigits="0"/>₫</p></div></div></div>
        <div class="col-lg-4 col-md-6 mb-4"><div class="card text-white bg-success shadow-sm h-100"><div class="card-body d-flex flex-column"><h5 class="card-title"><i class="bi bi-arrow-down-circle-fill"></i> Thu nhập tháng này</h5><p class="card-text fs-2 fw-bold">+ <fmt:formatNumber value="${monthlyIncome}" type="currency" currencySymbol="" groupingUsed="true" maxFractionDigits="0"/>₫</p><div class="mt-auto text-end"><button class="btn btn-light rounded-circle" data-bs-toggle="modal" data-bs-target="#addIncomeModal"><i class="bi bi-plus-lg fs-4"></i></button></div></div></div></div>
        <div class="col-lg-4 col-md-6 mb-4"><div class="card text-white bg-danger shadow-sm h-100"><div class="card-body d-flex flex-column"><h5 class="card-title"><i class="bi bi-arrow-up-circle-fill"></i> Chi tiêu tháng này</h5><p class="card-text fs-2 fw-bold">- <fmt:formatNumber value="${monthlyExpense}" type="currency" currencySymbol="" groupingUsed="true" maxFractionDigits="0"/>₫</p><div class="mt-auto text-end"><button class="btn btn-light rounded-circle" data-bs-toggle="modal" data-bs-target="#addExpenseModal"><i class="bi bi-plus-lg fs-4"></i></button></div></div></div></div>
    </div>
    <div class="row mt-3"><div class="col-12"><div class="card shadow-sm"><div class="card-header d-flex justify-content-between align-items-center"><h5 class="mb-0">Giao dịch gần đây</h5><a href="${pageContext.request.contextPath}/history" class="btn btn-sm btn-outline-secondary">Xem tất cả <i class="bi bi-arrow-right"></i></a></div><div class="card-body"><div class="table-responsive"><table class="table table-hover align-middle"><tbody><c:forEach var="txn" items="${transactionList}"><tr><td><div class="d-flex align-items-center"><div class="fs-3 me-3"><c:if test="${txn.type == 'income'}"><i class="bi bi-arrow-down-circle-fill text-success"></i></c:if><c:if test="${txn.type == 'expense'}"><i class="bi bi-arrow-up-circle-fill text-danger"></i></c:if></div><div><div class="fw-bold">${txn.note}</div><small class="text-muted"><fmt:formatDate value="${txn.transactionDate}" pattern="dd/MM/yyyy"/></small></div></div></td><td class="text-end fw-bold ${txn.type == 'income' ? 'text-success' : 'text-danger'} fs-5">${txn.type == 'income' ? '+' : '-'} <fmt:formatNumber value="${txn.amount}" type="currency" currencySymbol="" groupingUsed="true" maxFractionDigits="0"/>₫</td></tr></c:forEach></tbody></table></div></div></div></div></div>

    <%-- Nhúng các Modal ẩn --%>
    <c:import url="/inc/_add-income-modal.jsp" />
    <c:import url="/inc/_add-expense-modal.jsp" />
</main>