<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal fade" id="addIncomeModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form action="process-add-transaction" method="post" onsubmit="return validateForm('income')">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title">Thêm Khoản Thu Nhập</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="type" value="income">
                    <input type="hidden" id="incomeCategoryName" name="categoryName">

                    <div class="form-floating mb-3">
                        <input type="number" class="form-control" id="incomeAmount" name="amount" placeholder="Số tiền" required>
                        <label for="incomeAmount">Số tiền</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="date" class="form-control" id="incomeDate" name="transactionDate" required>
                        <label for="incomeDate">Ngày giao dịch</label>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Loại Thu Nhập:</label>
                        <div class="btn-group w-100" role="group" id="incomeCategoryButtons">
                            <button type="button" class="btn btn-outline-success" onclick="selectCategory('income', 'Lương')">Lương</button>
                            <button type="button" class="btn btn-outline-success" onclick="selectCategory('income', 'Thưởng')">Thưởng</button>
                            <button type="button" class="btn btn-outline-success" onclick="selectCategory('income', 'Khác')">Khác</button>
                        </div>
                    </div>
                    <div class="form-floating mb-3" id="incomeNoteContainer" style="display: none;">
                        <textarea class="form-control" placeholder="Ghi chú" id="incomeNote" name="note" style="height: 100px"></textarea>
                        <label for="incomeNote">Ghi chú</label>
                    </div>
                    <input type="hidden" id="incomeAccountIdHidden" name="accountId">
                    <div class="mb-3">
                        <label class="form-label">Loại Tiền:</label>
                        <div id="incomeAccountButtons">
                            <c:forEach var="acc" items="${accounts}">
                                <button type="button" class="btn btn-outline-success m-1" onclick="selectAccount('income', ${acc.accountId}, this)">${acc.name}</button>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit" class="btn btn-success">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>