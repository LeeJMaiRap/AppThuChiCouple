<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="vh-100">
    <div class="container-fluid h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-md-9 col-lg-6 col-xl-5">
                <img src="${pageContext.request.contextPath}/assets/images/thu-chi-main-logo.png"
                     class="img-fluid" alt="Ảnh minh họa">
            </div>
            <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                <div class="card shadow-sm border-0">
                    <div class="card-body p-5">
                        <h2 class="text-center mb-4">Đăng nhập</h2>
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success">${successMessage}</div>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/login" method="post">
                            <div class="form-floating mb-3">
                                <input type="text" class="form-control" id="username" name="username" placeholder="Tên đăng nhập" required>
                                <label for="username">Tên đăng nhập</label>
                            </div>
                            <div class="form-floating mb-3">
                                <input type="password" class="form-control" id="password" name="password" placeholder="Mật khẩu" required>
                                <label for="password">Mật khẩu</label>
                            </div>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger py-2">${error}</div>
                            </c:if>
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="true" id="remember-me" name="remember-me" checked>
                                    <label class="form-check-label" for="remember-me">Ghi nhớ đăng nhập</label>
                                </div>
                            </div>
                            <div class="d-grid">
                                <button class="btn btn-primary btn-lg" type="submit">Đăng nhập</button>
                            </div>
                            <p class="mt-4 text-center">Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a></p>
                            <p class="text-body text-decoration-none text-center"> <a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a></p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>