package com.asaki0019.website.api.user;

import com.asaki0019.website.model.User;
import com.asaki0019.website.repository.UserRepository;
import com.asaki0019.website.tools.JwtTool;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import java.io.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 用户登录API Servlet类
 * <p>
 * 该类负责处理用户的登录请求，验证用户信息，并生成JWT令牌返回给客户端。
 *
 * @author asaki0019
 * @version 1.1
 * @since 2024-11-11
 */
@WebServlet("/api/user/login")
public class Login extends HttpServlet {
    /**
     * 序列化ID，用于Java的序列化机制。
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户仓库，用于查询用户信息。
     */
    private final UserRepository userRepository = new UserRepository();

    // 日志记录器
    private static final Logger logger = Logger.getLogger(UserRepository.class.getName());
    // 错误码和消息常量
    private static final String ERROR_CODE_1 = "1";
    private static final String ERROR_CODE_2 = "2";
    private static final String ERROR_MESSAGE_VERIFICATION_CODE = "Verification code is incorrect.";
    private static final String ERROR_MESSAGE_INVALID_CREDENTIALS = "Username or password is incorrect.";

    /**
     * 处理POST请求的方法。
     * <p>
     * 从请求中读取JSON数据，验证验证码、用户名和密码，然后生成JWT令牌并返回给客户端。
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     * @throws IOException 如果发生I/O错误
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try (Reader reader = new InputStreamReader(request.getInputStream())) {

            // 从请求中读取JSON数据
            JSONObject requestBody = new JSONObject(reader);

            // 获取JSON参数
            String account = requestBody.getString("account");
            String password = requestBody.getString("password");
            String verified = requestBody.getString("verified");

            // 验证验证码
            if (!verifyCaptchaCode(request, verified)) {
                writeErrorResponse(out, HttpServletResponse.SC_BAD_REQUEST, ERROR_CODE_1, ERROR_MESSAGE_VERIFICATION_CODE, response);
                return;
            }

            // 验证用户名和密码
            Optional<User> userOpt = Optional.ofNullable(userRepository.findByUsername(account));
            if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
                writeErrorResponse(out, HttpServletResponse.SC_UNAUTHORIZED, ERROR_CODE_2, ERROR_MESSAGE_INVALID_CREDENTIALS, response);
                return;
            }

            // 生成JWT令牌并返回响应
            User user = userOpt.get();
            String token = JwtTool.generateToken(account, user.getRole());
            JSONObject successResponse = new JSONObject()
                    .put("error_code", 0)
                    .put("data", new JSONObject()
                            .put("token", token)
                            .put("name", user.getName())
                            .put("role", user.getRole()));

            writeResponse(out, HttpServletResponse.SC_OK, successResponse, response);
        }  catch (Exception e){
            logger.log(Level.SEVERE, "Error : " , e);
            writeErrorResponse(out, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "3", "An error occurred while processing the request.", response);
        }
    }

    /**
     * 验证验证码。
     *
     * @param request  HTTP请求对象
     * @param verified 用户提供的验证码
     * @return 验证码是否正确
     */
    private boolean verifyCaptchaCode(HttpServletRequest request, String verified) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String captchaCode = (String) session.getAttribute("captchaCode");
            return captchaCode != null && captchaCode.equals(verified);
        }
        return false;
    }

    /**
     * 写入错误响应的方法。
     *
     * @param out       PrintWriter对象，用于写入响应输出流
     * @param statusCode 响应状态码
     * @param errorCode  错误码
     * @param message    错误消息
     * @param response  HTTP响应对象
     */
    private void writeErrorResponse(PrintWriter out, int statusCode, String errorCode, String message, HttpServletResponse response) {
        JSONObject json = new JSONObject()
                .put("error_code", errorCode)
                .put("message", message);
        writeResponse(out, statusCode, json, response);
    }

    /**
     * 写入响应的方法。
     * <p>
     * 设置响应状态码，将JSON数据写入响应输出流，并刷新输出流。
     *
     * @param out       PrintWriter对象，用于写入响应输出流
     * @param statusCode 响应状态码
     * @param json      要写入响应的JSON对象
     * @param response  HTTP响应对象
     */
    private void writeResponse(PrintWriter out, int statusCode, JSONObject json, HttpServletResponse response) {
        response.setStatus(statusCode);
        out.print(json.toString());
        out.flush();
    }
}