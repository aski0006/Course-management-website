package api;

import com.asaki0019.project.api.user.Login;
import com.asaki0019.project.model.User;
import com.asaki0019.project.repository.UserRepository;
import com.asaki0019.project.tools.JwtTool;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class LoginTest {

    private Login loginServlet;
    private UserRepository userRepository;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    public void setUp() {
        loginServlet = new Login();
        userRepository = mock(UserRepository.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);

        // 使用反射设置私有字段 userRepository
        try {
            java.lang.reflect.Field field = Login.class.getDeclaredField("userRepository");
            field.setAccessible(true);
            field.set(loginServlet, userRepository);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoginSuccess() throws IOException, ServletException {
        // 模拟用户数据
        User user = new User("testUser", "password", "Test User", "USER");

        // 模拟请求参数
        when(request.getReader()).thenReturn(new java.io.BufferedReader(new java.io.StringReader(
                "{\"account\":\"testUser\",\"password\":\"password\",\"verified\":\"validCaptcha\"}"
        )));

        // 模拟Session和验证码
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("captchaCode")).thenReturn("validCaptcha");

        // 模拟用户查询
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        // 模拟JWT生成
        when(JwtTool.generateToken(anyString(), anyString())).thenReturn("mockedToken");

        // 模拟响应
        when(response.getWriter()).thenReturn(printWriter);

        // 执行登录请求
        loginServlet.doPost(request, response);

        // 验证响应
        printWriter.flush();
        JSONObject responseJson = new JSONObject(stringWriter.toString());
        assertEquals(0, responseJson.getInt("error_code"));
        assertEquals("mockedToken", responseJson.getJSONObject("data").getString("token"));
        assertEquals("Test User", responseJson.getJSONObject("data").getString("name"));
        assertEquals("USER", responseJson.getJSONObject("data").getString("role"));
    }

    @Test
    public void testLoginFailureInvalidCredentials() throws IOException, ServletException {
        // 模拟请求参数
        when(request.getReader()).thenReturn(new java.io.BufferedReader(new java.io.StringReader(
                "{\"account\":\"testUser\",\"password\":\"wrongPassword\",\"verified\":\"validCaptcha\"}"
        )));

        // 模拟Session和验证码
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("captchaCode")).thenReturn("validCaptcha");

        // 模拟用户查询
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        // 模拟响应
        when(response.getWriter()).thenReturn(printWriter);

        // 执行登录请求
        loginServlet.doPost(request, response);

        // 验证响应
        printWriter.flush();
        JSONObject responseJson = new JSONObject(stringWriter.toString());
        assertEquals("2", responseJson.getString("error_code"));
        assertEquals("Username or password is incorrect.", responseJson.getString("message"));
    }

    @Test
    public void testLoginFailureInvalidCaptcha() throws IOException, ServletException {
        // 模拟请求参数
        when(request.getReader()).thenReturn(new java.io.BufferedReader(new java.io.StringReader(
                "{\"account\":\"testUser\",\"password\":\"password\",\"verified\":\"invalidCaptcha\"}"
        )));

        // 模拟Session和验证码
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("captchaCode")).thenReturn("validCaptcha");

        // 模拟响应
        when(response.getWriter()).thenReturn(printWriter);

        // 执行登录请求
        loginServlet.doPost(request, response);

        // 验证响应
        printWriter.flush();
        JSONObject responseJson = new JSONObject(stringWriter.toString());
        assertEquals("1", responseJson.getString("error_code"));
        assertEquals("Verification code is incorrect.", responseJson.getString("message"));
    }
}