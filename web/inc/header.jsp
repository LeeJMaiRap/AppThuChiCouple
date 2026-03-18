<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="vi">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Quản lý Thu Chi</title>
        <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

        <style>
            body, html {
                height: 100%;
                overflow: hidden; /* Ngăn trang cuộn, chỉ cho phép content cuộn */
            }
            .main-layout {
                display: flex;
                height: 100vh;
            }
            .sidebar {
                width: 280px;
                flex-shrink: 0;
                transition: margin-left 0.3s;
            }
            .content-wrapper {
                flex-grow: 1;
                overflow-y: auto; /* Chỉ cho phép khu vực này cuộn */
                height: 100vh;
            }
            /* CSS cho responsive mobile */
            @media (max-width: 992px) {
                .sidebar {
                    position: fixed; /* Giúp sidebar nổi lên trên nội dung */
                    top: 0;
                    bottom: 0;
                    left: 0;
                    z-index: 1030; /* Nằm trên hầu hết các thành phần khác */
                    margin-left: -280px; /* Ẩn sidebar mặc định */
                }
                body.sidebar-visible .sidebar {
                    margin-left: 0; /* Hiện sidebar khi có class */
                }
            }
        </style>

        <script>
            function toggleSidebar() {
                document.body.classList.toggle('sidebar-visible');
            }
        </script>
    </head>
    <body class="bg-light">