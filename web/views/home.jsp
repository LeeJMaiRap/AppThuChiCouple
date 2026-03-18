<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/inc/header.jsp" />

<div class="main-layout">
    <c:import url="/inc/_sidebar.jsp" />
    <div class="content-wrapper">
        <c:import url="/inc/_home-content.jsp" />
    </div>
</div>

<script>
    function selectCategory(type, categoryName) {
        document.getElementById(type + 'CategoryName').value = categoryName;
        const buttonGroup = document.getElementById(type + 'CategoryButtons');
        const buttons = buttonGroup.querySelectorAll('button');
        buttons.forEach(btn => {
            btn.classList.remove('active');
            if (btn.innerText === categoryName)
                btn.classList.add('active');
        });
        document.getElementById(type + 'NoteContainer').style.display = (categoryName === 'Khác') ? 'block' : 'none';
    }
    function selectAccount(type, accountId, btnElement) {
        document.getElementById(type + 'AccountIdHidden').value = accountId;
        const buttonGroup = document.getElementById(type + 'AccountButtons');
        const buttons = buttonGroup.querySelectorAll('button');
        buttons.forEach(btn => btn.classList.remove('active'));
        btnElement.classList.add('active');
    }
    function validateForm(type) {
        if (!document.getElementById(type + 'CategoryName').value) {
            alert('Vui lòng chọn một danh mục.');
            return false;
        }
        if (!document.getElementById(type + 'AccountIdHidden').value) {
            alert('Vui lòng chọn một tài khoản.');
            return false;
        }
        return true;
    }
    document.addEventListener('DOMContentLoaded', function () {
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('incomeDate').value = today;
        document.getElementById('expenseDate').value = today;
        const firstIncomeButton = document.querySelector('#incomeAccountButtons button');
        if (firstIncomeButton)
            firstIncomeButton.click();
        const firstExpenseButton = document.querySelector('#expenseAccountButtons button');
        if (firstExpenseButton)
            firstExpenseButton.click();
    });
</script>        

<c:import url="/inc/footer.jsp" />