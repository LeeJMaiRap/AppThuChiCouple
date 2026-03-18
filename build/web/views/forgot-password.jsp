<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/inc/header.jsp" />

<main class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="card shadow-sm">
                <div class="card-body p-5">
                    <h1 class="card-title text-center mb-4">Quên mật khẩu</h1>
                    <p class="text-center text-muted mb-4">Nhập email của bạn để nhận hướng dẫn đặt lại mật khẩu.</p>
                    
                    <c:if test="${not empty message}"><div class="alert alert-success">${message}</div></c:if>
                    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

                    <form action="forgot-password" method="post">
                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email" required>
                            <label for="email">Địa chỉ Email</label>
                        </div>
                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-primary btn-lg">Gửi yêu cầu</button>
                        </div>
                        <div class="text-center mt-3">
                            <a href="login">Quay lại trang Đăng nhập</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<c:import url="/inc/footer.jsp" />