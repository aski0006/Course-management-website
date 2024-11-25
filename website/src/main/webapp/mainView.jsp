<%--
  Created by IntelliJ IDEA.
  User: bronya
  Date: 2024/11/21
  Time: 0:08
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: bronya
  Date: 2024/11/21
  Time: 0:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课程管理网站</title>
    <link rel="stylesheet" href="css/mainView.css">
</head>
<body>
<div class="header"></div>
<input type="checkbox" class="openSidebarMenu" id="openSidebarMenu">
<label for="openSidebarMenu" class="sidebarIconToggle">
    <div class="spinner diagonal part-1"></div>
    <div class="spinner horizontal"></div>
    <div class="spinner diagonal part-2"></div>
</label>
<div id="sidebarMenu">
    <ul class="sidebarMenuInner">
        <li>课程管理系统<span>Course Manage System</span></li>
        <li class="avatar-container">
            <p id="avatar-name">default</p>
            <div id="avatar">
                <img src="static/default.jpg" alt="avatar" id="userAvatar">
            </div>
        </li>
        <li><a href="#" target="_blank">用户</a></li>
        <li><a href="#" target="_blank">作业</a></li>
        <li><a href="#" target="_blank">测试</a></li>
        <li><a href="#" target="_blank">消息</a></li>
        <li><a href="#" target="_blank">设置</a></li>
    </ul>
</div>
<div id='center' class="main center">
    <div class="mainInner">
        <div>主界面</div>
    </div>
</div>
<script src="javascript/mainView.js"></script>
</body>
</html>