<%-- File: views/profile.jsp (Layout đã cập nhật) --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/inc/header.jsp" />

<div class="main-layout">
    <c:import url="/inc/_sidebar.jsp" />

    <div class="content-wrapper">
        <main class="flex-grow-1 p-4">
             <%-- Header cho mobile --%>
            <div class="d-lg-none d-flex justify-content-between align-items-center mb-3">
                <h4 class="mb-0">Thông tin cá nhân</h4>
                <button class="btn btn-outline-secondary" type="button" onclick="toggleSidebar()">
                    <i class="bi bi-list fs-4"></i>
                </button>
            </div>
            
            <c:import url="/inc/_profile-form.jsp" />
        </main>
    </div>
</div>

<c:import url="/inc/footer.jsp" />