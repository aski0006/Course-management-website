package com.asaki0019.website.api.user;

import com.asaki0019.website.repository.UserRepository;
import com.asaki0019.website.repository.UserRepositoryImpl;
import com.asaki0019.website.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static com.asaki0019.website.tools.JsonUtils.*;

@WebServlet(name="login", value = "/api/user/login")
public class LoginServlet extends HttpServlet {
    // 使用依赖注入
    private UserServiceImpl userServiceImpl;


    public void init() throws ServletException {
        super.init();
        userServiceImpl = new UserServiceImpl( new UserRepositoryImpl());
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter();
             BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(request.getInputStream()))) {
            JSONObject requestBody = new JSONObject(readJsonBody(reader));
            JSONObject result = userServiceImpl.login(request, requestBody);
            response.setStatus(HttpServletResponse.SC_OK);
            writeResponse(result, out);
        } catch (Exception e) {
            writeErrorResponse(response);
        }
    }
}