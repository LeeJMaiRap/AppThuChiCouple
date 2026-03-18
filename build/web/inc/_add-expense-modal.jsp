<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal fade" id="addExpenseModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form action="process-add-transaction" method="post" onsubmit="return validateForm('expense')">
                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title">Thêm Khoản Chi Tiêu</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="type" value="expense">
                    <input type="hidden" id="expenseCategoryName" name="categoryName">
                    
                    <div class="form-floating mb-3">
                        <input type="number" class="form-control" id="expenseAmount" name="amount" placeholder="Số tiền" required>
                        <label for="expenseAmount">Số tiền</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="date" class="form-control" id="expenseDate" name="transactionDate" required>
                        <label for="expenseDate">Ngày giao dịch</label>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Loại Chi Tiêu:</label>
                        <div class="btn-group flex-wrap w-100" role="group" id="expenseCategoryButtons">
                            <button type="button" class="btn btn-outline-danger m-1" onclick="selectCategory('expense', 'Ăn uống')">Ăn uống</button>
                            <button type="button" class="btn btn-outline-danger m-1" onclick="selectCategory('expense', 'Mua sắm')">Mua sắm</button>
                            <button type="button" class="btn btn-outline-danger m-1" onclick="selectCategory('expense', 'Di chuyển')">Đi lại</button>
<!--                            <button type="button" class="btn btn-outline-danger m-1" onclick="selectCategory('expense', 'Hóa đơn')">Hóa đơn</button>-->
                            <button type="button" class="btn btn-outline-danger m-1" onclick="selectCategory('expense', 'Khác')">Khác</button>
                        </div>
                    </div>
                    <div class="form-floating mb-3" id="expenseNoteContainer" style="display: none;">
                        <textarea class="form-control" placeholder="Ghi chú" id="expenseNote" name="note" style="height: 100px"></textarea>
                        <label for="expenseNote">Ghi chú</label>
                    </div>
                    <input type="hidden" id="expenseAccountIdHidden" name="accountId">
                    <div class="mb-3">
                        <label class="form-label">Loại Tiền:</label>
                        <div id="expenseAccountButtons">
                            <c:forEach var="acc" items="${accounts}">
                                <button type="button" class="btn btn-outline-danger m-1" onclick="selectAccount('expense', ${acc.accountId}, this)">${acc.name}</button>
                            </c:forEach>
                        </div>
                    </div>
                    
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit" class="btn btn-danger">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>