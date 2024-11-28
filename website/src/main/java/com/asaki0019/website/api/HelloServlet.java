package com.asaki0019.website.api;

import com.asaki0019.website.tools.JwtTool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求头中的Token
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // 去掉 "Bearer " 前缀

            // 假设有一个方法可以解析Token并获取role
            String role = JwtTool.extractRole(token);

            // 根据role返回不同的JSON数据
            String jsonResponse = "{}";
            if ("student".equals(role)) {
                jsonResponse = "{\"current\": [{\"name\": \"作业1\"}, {\"name\": \"作业2\"}], \"completed\": [{\"name\": \"已完成作业1\"}], \"upcoming\": [{\"name\": \"即将发布作业1\"}]}";
            } else if ("teacher".equals(role)) {
                // 返回教师的数据
            }

            // 设置响应的Content-Type
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or missing token");
        }
    }
}