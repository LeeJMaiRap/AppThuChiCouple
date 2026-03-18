<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/inc/header.jsp" />

<main class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="card shadow-sm">
                <div class="card-body p-5">
                    <h1 class="card-title text-center mb-4">Tạo Mật khẩu Mới</h1>
                    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
                    <form action="set-new-password" method="post">
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="Mật khẩu mới" required>
                            <label for="newPassword">Mật khẩu mới</label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Xác nhận mật khẩu mới" required>
                            <label for="confirmPassword">Xác nhận mật khẩu mới</label>
                        </div>
                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-primary btn-lg">Lưu mật khẩu</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<c:import url="/inc/footer.jsp" />