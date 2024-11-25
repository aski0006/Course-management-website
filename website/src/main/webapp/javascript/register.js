document.addEventListener('DOMContentLoaded', function() {
    // 初始化验证码图片
    refreshCaptcha();

    // 点击验证码图片刷新验证码
    document.getElementById('captchaImage').addEventListener('click', refreshCaptcha);

    // 注册表单提交
    document.getElementById('registerForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const name = document.getElementById('name').value;
        const password = document.getElementById('password').value;
        const verifiedCode = document.getElementById('verifiedCode').value;
        const role = document.querySelector('input[name="user_type"]:checked').value;

        const requestData = {
            username: username,
            name: name,
            password: password,
            verifiedCode: verifiedCode,
            role: role
        };


        // fetch('https://9f966u1393.goho.co/api/user/register', {
        //本地测试用
        fetch('api/user/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.error_code === 0) {
                    document.getElementById('message').textContent = '注册成功';
                    document.getElementById('message').style.color = '#5cb85c';
                    // 可以在这里处理注册成功后的跳转逻辑
                    window.location.href = "login.jsp"
                } else {
                    if (data.message && typeof data.message === 'string') {
                        alert(data.message);
                        document.getElementById('message').textContent = data.message;
                        document.getElementById('message').style.color = '#d9534f';
                    } else {
                        alert('注册失败，请重试。');
                        document.getElementById('message').textContent = '注册失败，请重试。';
                        document.getElementById('message').style.color = '#d9534f';
                    }
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
    // captchaImage.src = 'https://9f966u1393.goho.co/api/utilss/captcha?t=' + new Date().getTime();
    //在本地进行测试
    captchaImage.src = 'api/utils/captcha?t=' + new Date().getTime();
}