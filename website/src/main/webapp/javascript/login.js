document.addEventListener('DOMContentLoaded', function() {
    // 初始化验证码图片
    refreshCaptcha();

    // 点击验证码图片刷新验证码
    document.getElementById('captchaImage').addEventListener('click', refreshCaptcha);

    // 登录表单提交
    document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const verificationCode = document.getElementById('verificationCode').value;

        const requestData = {
            account: username,
            password: password,
            verified: verificationCode
        };

        // fetch('https://9f966u1193.goho.co/api/user/login', {
        // 本地测试用
        fetch('api/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.error_code === 0) {
                    document.getElementById('message').textContent = '登录成功';
                    document.getElementById('message').style.color = '#5cb85c';
                    // 可以在这里处理登录成功后的跳转逻辑

                } else {
                    document.getElementById('message').textContent = '登录失败';
                    document.getElementById('message').style.color = '#d9534f';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById('message').textContent = '网络错误，请稍后再试';
                document.getElementById('message').style.color = '#d9534f';
            });
    });
});

function refreshCaptcha() {
    const captchaImage = document.getElementById('captchaImage');
    // captchaImage.src = 'https://9f966u1193.goho.co/api/tools/captcha?t=' + new Date().getTime();
    // 本地测试用
    captchaImage.src = 'api/tools/captcha?t=' + new Date().getTime();
}