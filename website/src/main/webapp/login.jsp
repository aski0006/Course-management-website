<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<div class="form-div">
    <div class="login-content">
        <h1>欢迎登录</h1>
        <span>没有账号？</span> <a href="register.jsp">注册</a>
    </div>
    <form id="loginForm" action="api/user/login" method="post">
        <table>
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
                    <label for="verificationCode"></label>
                    <input name="verificationCode" type="text" id="verificationCode" required>
                    <br>
                    <span id="verificationCode_err" class="err_msg" style="display: none">验证码错误</span>
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
            <input value="登 录" type="submit" id="login_btn">
        </div>
        <br class="clear">
    </form>
    <p id="message" class="message"></p>
</div>

<script src="javascript/login.js"></script>
</body>
</html>
