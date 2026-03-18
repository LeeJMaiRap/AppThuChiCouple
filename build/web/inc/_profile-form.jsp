<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="container mt-4 mb-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card shadow-sm">
                <div class="card-body p-4">
                    <h1 class="card-title text-center mb-4">Thông tin cá nhân</h1>
                    <form action="${pageContext.request.contextPath}/update-profile" method="post">
                        
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="fullName" name="fullName" value="${sessionScope.user.fullName}" placeholder="Họ và Tên" required>
                            <label for="fullName">Họ và Tên</label>
                        </div>
                         <div class="form-floating mb-3">
                            <input type="email" class="form-control" id="email" name="email" value="${sessionScope.user.email}" placeholder="Email">
                            <label for="email">Email</label>
                        </div>
                         <div class="form-floating mb-3">
                            <input type="tel" class="form-control" id="phone" name="phone" value="${sessionScope.user.phone}" placeholder="Số điện thoại">
                            <label for="phone">Số điện thoại</label>
                        </div>
                        
                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-primary btn-lg">Lưu thay đổi</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>