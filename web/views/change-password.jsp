<%-- File: views/change-password.jsp (Layout đã cập nhật) --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/inc/header.jsp" />

<div class="main-layout">
    <c:import url="/inc/_sidebar.jsp" />

    <div class="content-wrapper">
        <%-- Nội dung chính của trang đổi mật khẩu --%>
        <main class="flex-grow-1 p-4">
            <%-- Header cho mobile --%>
            <div class="d-lg-none d-flex justify-content-between align-items-center mb-3">
                <h4 class="mb-0">Đổi mật khẩu</h4>
                <button class="btn btn-outline-secondary" type="button" onclick="toggleSidebar()">
                    <i class="bi bi-list fs-4"></i>
                </button>
            </div>

            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="card shadow-sm">
                        <div class="card-body p-5">
                            <h1 class="card-title text-center mb-4 d-none d-lg-block">Đổi mật khẩu</h1>

                            <c:if test="${not empty message}"><div class="alert alert-success">${message}</div></c:if>
                            <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

                            <form action="${pageContext.request.contextPath}/process-change-password" method="post">
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" id="oldPassword" name="oldPassword" placeholder="Mật khẩu cũ" required>
                                    <label for="oldPassword">Mật khẩu cũ</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="Mật khẩu mới" required>
                                    <label for="newPassword">Mật khẩu mới</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Xác nhận mật khẩu mới" required>
                                    <label for="confirmPassword">Xác nhận mật khẩu mới</label>
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
    </div>
</div>

<c:import url="/inc/footer.jsp" />