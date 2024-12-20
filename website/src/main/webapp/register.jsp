<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>注册</title>
  <link rel="stylesheet" href="css/register.css">
</head>
<body>
<div class="form-div">
  <div class="reg-content">
    <h1>欢迎注册</h1>
    <span>已有账号？</span> <a href="login.jsp">登录</a>
  </div>
  <form id="registerForm" action="api/user/register" method="post">
    <table>
      <tr>
        <td>注册类型</td>
        <td class="inputs">
          <input type="radio" name="user_type" value="teacher" id="teacher" required>
          <label for="teacher">教师</label>
          <input type="radio" name="user_type" value="student" id="student" required>
          <label for="student">学生</label>
        </td>
      </tr>
      <tr>
        <td>账号</td>
        <td class="inputs">
          <label for="username"></label>
          <input name="username" type="text" id="username" required>
          <br>
          <span id="username_err" class="err_msg" style="display: none">用户名出错啦</span>
        </td>
      </tr>
      <tr>
        <td>昵称</td>
        <td class="inputs">
          <label for="name"></label>
          <input name="name" type="text" id="name" required>
          <br>
          <span id="name_err" class="err_msg" style="display: none">昵称不太受欢迎</span>
        </td>
      </tr>
      <tr>
        <td>密码</td>
        <td class="inputs">
          <label for="password"></label>
          <input name="password" type="password" id="password" required>
          <br>
          <span id="password_err" class="err_msg" style="display: none">密码格式有误</span>
        </td>
      </tr>
      <tr>
        <td>验证码</td>
        <td class="inputs">
          <label for="verifiedCode"></label>
          <input name="verifiedCode" type="text" id="verifiedCode" required>
          <br>
          <span id="verifiedCode_err" class="err_msg" style="display: none">验证码错误</span>
        </td>
      </tr>
      <tr>
        <td></td>
        <td class="inputs captcha">
          <img id="captchaImage" alt="验证码" style="cursor: pointer" src="api/utils/captcha">
        </td>
      </tr>
    </table>
    <div class="buttons">
      <input value="注 册" type="submit" id="reg_btn">
    </div>
    <br class="clear">
  </form>
  <p id="message" class="message"></p>
</div>

<script src="javascript/register.js"></script>
</body>
</html>