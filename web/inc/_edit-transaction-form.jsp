<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="content-wrapper">
    <main class="flex-grow-1 p-4">
        <div class="d-lg-none d-flex justify-content-between align-items-center mb-3">
            <h4 class="mb-0">Sửa Giao Dịch</h4>
            <button class="btn btn-outline-secondary" type="button" onclick="toggleSidebar()">
                <i class="bi bi-list fs-4"></i>
            </button>
        </div>

        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card shadow-sm">
                    <div class="card-body p-5">
                        <h1 class="card-title text-center mb-4 d-none d-lg-block">Sửa Giao Dịch</h1>
                        <form action="${pageContext.request.contextPath}/process-edit-transaction" method="post">

                            <input type="hidden" name="transactionId" value="${transaction.transactionId}">
                            <input type="hidden" name="type" value="${transaction.type}">
                            <input type="hidden" id="editCategoryId" name="categoryId" value="${transaction.categoryId}">
                            <input type="hidden" id="editAccountId" name="accountId" value="${transaction.accountId}">

                            <div class="form-floating mb-3">
                                <input type="number" class="form-control" id="amount" name="amount" required step="1000" value="${transaction.amount}" placeholder="Số tiền">
                                <label for="amount">Số tiền</label>
                            </div>
                            <div class="form-floating mb-3">
                                <fmt:formatDate value="${transaction.transactionDate}" pattern="yyyy-MM-dd" var="formattedDate"/>
                                <input type="date" class="form-control" id="transactionDate" name="transactionDate" required value="${formattedDate}" placeholder="Ngày giao dịch">
                                <label for="transactionDate">Ngày giao dịch</label>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Danh mục:</label>
                                <div id="categoryButtons" class="btn-group flex-wrap w-100">
                                    <c:forEach var="cat" items="${categories}">
                                        <c:if test="${cat.type == transaction.type}">
                                            <button type="button" class="btn btn-outline-primary m-1" data-id="${cat.categoryId}" onclick="selectItem('category', ${cat.categoryId}, this)">
                                                ${cat.name}
                                            </button>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Tài khoản:</label>
                                <div id="accountButtons" class="btn-group flex-wrap w-100">
                                    <c:forEach var="acc" items="${accounts}">
                                        <button type="button" class="btn btn-outline-secondary m-1" data-id="${acc.accountId}" onclick="selectItem('account', ${acc.accountId}, this)">
                                            ${acc.name}
                                        </button>
                                    </c:forEach>
                                </div>
                            </div>

                            <div class="form-floating mb-3">
                                <textarea class="form-control" placeholder="Ghi chú" id="note" name="note" style="height: 100px">${transaction.note}</textarea>
                                <label for="note">Ghi chú</label>
                            </div>

                            <div class="d-grid mt-4">
                                <button type="submit" class="btn btn-primary btn-lg">Lưu Thay Đổi</button>
                                <a href="${pageContext.request.contextPath}/history" class="btn btn-link text-secondary mt-2">Hủy</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>

<script>
    function selectItem(type, id, btnElement) {
        if (type === 'category') {
            document.getElementById('editCategoryId').value = id;
        } else {
            document.getElementById('editAccountId').value = id;
        }

        const buttonGroup = btnElement.parentElement;
        const buttons = buttonGroup.querySelectorAll('button');
        buttons.forEach(btn => btn.classList.remove('active'));
        btnElement.classList.add('active');
    }

    document.addEventListener('DOMContentLoaded', function () {
        const currentCategoryId = '${transaction.categoryId}';
        const currentAccountId = '${transaction.accountId}';

        const categoryButton = document.querySelector(`#categoryButtons button[data-id='${currentCategoryId}']`);
        if (categoryButton) {
            categoryButton.click();
        }

        const accountButton = document.querySelector(`#accountButtons button[data-id='${currentAccountId}']`);
        if (accountButton) {
            accountButton.click();
        }
    });
</script>