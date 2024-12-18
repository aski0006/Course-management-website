<%--
  Created by IntelliJ IDEA.
  User: bronya
  Date: 2024/11/25
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>作业</title>
    <link rel="stylesheet" href="css/homework.css">
</head>
<body>
<div class="nav">
    <h1>作业管理</h1>
    <div class="avatar-container">
        <p id="avatar-name">default</p>
        <div id="avatar">
            <img src="static/default.jpg" alt="avatar" id="userAvatar">
        </div>
    </div>
</div>
<div class="homework-container">
    <div class="homework-list">
        <h2>超时作业</h2>
        <ul id="overdueHomework">
            <!-- 动态内容 -->
        </ul>
    </div>
    <div class="homework-list">
        <h2>当前作业</h2>
        <ul id="currentHomework">
            <!-- 动态内容 -->
        </ul>
    </div>
    <div class="homework-list">
        <h2>已完成作业</h2>
        <ul id="completedHomework">
            <!-- 动态内容 -->
        </ul>
    </div>
    <div class="homework-list">
        <h2>未发布作业</h2>
        <ul id="unpublishedHomework">
            <!-- 动态内容 -->
        </ul>
    </div>
</div>
<script src="javascript/homework.js"></script>
</body>
</html>
