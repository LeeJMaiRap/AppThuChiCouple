<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/inc/header.jsp" />

<div class="main-layout">
    <c:import url="/inc/_sidebar.jsp" />
    <div class="content-wrapper">
        <main class="flex-grow-1 p-4">
            <h2 class="mb-4">Thống kê</h2>
            <div class="row">
                <div class="col-lg-5 mb-4">
                    <div class="card shadow-sm h-100">
                        <div class="card-body">
                            <h5 class="card-title">Chi tiêu theo danh mục (Tháng này)</h5>
                            <canvas id="expensePieChart"></canvas>
                        </div>
                    </div>
                </div>
                <div class="col-lg-7 mb-4">
                    <div class="card shadow-sm h-100">
                        <div class="card-body">
                            <h5 class="card-title">Thu chi 6 tháng gần nhất</h5>
                            <canvas id="summaryBarChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const expenseData = JSON.parse('${expenseDataJson}');
        const pieLabels = expenseData.map(item => item.categoryName);
        const pieData = expenseData.map(item => item.totalAmount);

        new Chart(document.getElementById('expensePieChart'), {
            type: 'pie',
            data: {
                labels: pieLabels,
                datasets: [{
                    label: 'Chi tiêu',
                    data: pieData,
                    backgroundColor: ['#dc3545', '#fd7e14', '#ffc107', '#198754', '#0dcaf0', '#6f42c1']
                }]
            }
        });

        const summaryData = JSON.parse('${summaryDataJson}');
        const barLabels = summaryData.map(item => item.month);
        const incomeData = summaryData.map(item => item.totalIncome);
        const expenseBarData = summaryData.map(item => item.totalExpense);

        new Chart(document.getElementById('summaryBarChart'), {
            type: 'bar',
            data: {
                labels: barLabels,
                datasets: [
                    {
                        label: 'Tổng Thu nhập',
                        data: incomeData,
                        backgroundColor: 'rgba(25, 135, 84, 0.7)',
                        borderColor: 'rgba(25, 135, 84, 1)',
                        borderWidth: 1
                    },
                    {
                        label: 'Tổng Chi tiêu',
                        data: expenseBarData,
                        backgroundColor: 'rgba(220, 53, 69, 0.7)',
                        borderColor: 'rgba(220, 53, 69, 1)',
                        borderWidth: 1
                    }
                ]
            },
            options: { scales: { y: { beginAtZero: true } } }
        });
    });
</script>

<c:import url="/inc/footer.jsp" />