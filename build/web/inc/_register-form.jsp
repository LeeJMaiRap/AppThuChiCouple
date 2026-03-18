<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<section class="vh-100">
    <div class="container-fluid h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-md-9 col-lg-6 col-xl-5">
                <img src="${pageContext.request.contextPath}/assets/images/thu-chi-main-logo.png"
                     class="img-fluid" alt="Thu Chi App">
            </div>
            <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                 <div class="card shadow-sm border-0">
                    <div class="card-body p-5">
                        <h2 class="text-center mb-4">Đăng ký tài khoản</h2>
                        <form action="${pageContext.request.contextPath}/register" method="post">
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
                            <div class="d-grid">
                                <button type="submit" class="btn btn-success btn-lg">Đăng ký</button>
                            </div>
                             <p class="mt-4 text-center">Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>