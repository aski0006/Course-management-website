package com.asaki0019.website.service;

import com.asaki0019.website.models.User;
import com.asaki0019.website.repository.UserRepository;
import com.asaki0019.website.tools.JwtTool;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    private static final String ERROR_MESSAGE_USER_EXISTS = "用户已存在";
    private final UserRepository userRepository;
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private static final String ERROR_CODE_1 = "1";
    private static final String ERROR_CODE_2 = "2";
    private static final String ERROR_MESSAGE_VERIFICATION_CODE = "验证码错误";
    private static final String ERROR_MESSAGE_INVALID_CREDENTIALS = "用户名或密码错误";

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public JSONObject login(HttpServletRequest request, JSONObject requestBody) {
        try {
            String account = requestBody.getString("account");
            String password = requestBody.getString("password");
            String verified = requestBody.getString("verified");

            if (!verifyCaptchaCode(request, verified)) {
                return createErrorResponse(ERROR_CODE_1, ERROR_MESSAGE_VERIFICATION_CODE);
            }

            Optional<User> userOpt = Optional.ofNullable(userRepository.findByUsername(account));
            if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
                return createErrorResponse(ERROR_CODE_2, ERROR_MESSAGE_INVALID_CREDENTIALS);
            }

            User user = userOpt.get();
            String token = JwtTool.generateToken(account, user.getRole());
            return new JSONObject()
                    .put("error_code", 0)
                    .put("data", new JSONObject()
                            .put("token", token)
                            .put("name", user.getName())
                            .put("role", user.getRole()));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error:", e);
            return createErrorResponse("3", "An error occurred while processing the request.");
        }
    }

    public JSONObject register(HttpServletRequest request, JSONObject requestBody) {
        try {
            String username = requestBody.getString("username");
            String name = requestBody.getString("name");
            String password = requestBody.getString("password");
            String verified = requestBody.getString("verifiedCode");
            String role = requestBody.getString("role");

            // 验证验证码
            if (!verifyCaptchaCode(request, verified)) {
                return createErrorResponse(ERROR_CODE_1, ERROR_MESSAGE_VERIFICATION_CODE);
            }

            // 检查用户是否重复
            if (userRepository.findByUsername(username) != null) {
                return createErrorResponse(ERROR_CODE_2, ERROR_MESSAGE_USER_EXISTS);
            }

            // 创建新用户
            User newUser = new User(username, password, name, role);
            newUser.setCreatedTime(LocalDateTime.now());
            newUser.setUpdatedTime(LocalDateTime.now());

            // 保存到数据库
            userRepository.save(newUser);

            return new JSONObject()
                    .put("error_code", 0)
                    .put("message", "注册成功");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error:", e);
            return createErrorResponse("3", "注册过程中发生错误");
        }
    }

    private boolean verifyCaptchaCode(HttpServletRequest request, String verified) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String captchaCode = (String) session.getAttribute("captchaCode");
            return captchaCode != null && captchaCode.equals(verified);
        } else {
            logger.log(Level.SEVERE, "An error occurred while session is null");
        }
        return false;
    }

    private JSONObject createErrorResponse(String errorCode, String message) {
        return new JSONObject()
                .put("error_code", errorCode)
                .put("message", message);
    }
}