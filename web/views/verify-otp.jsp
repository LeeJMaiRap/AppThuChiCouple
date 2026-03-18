<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/inc/header.jsp" />

<main class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="card shadow-sm">
                <div class="card-body p-5">
                    <h1 class="card-title text-center mb-4">Xác thực OTP</h1>
                    <p class="text-center text-muted mb-4">Một mã OTP đã được gửi đến email của bạn. Vui lòng nhập vào đây.</p>
                    
                    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

                    <form action="verify-otp" method="post">
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="otp" name="otp" placeholder="Mã OTP" required>
                            <label for="otp">Mã OTP</label>
                        </div>
                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-primary btn-lg">Xác nhận</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<c:import url="/inc/footer.jsp" />