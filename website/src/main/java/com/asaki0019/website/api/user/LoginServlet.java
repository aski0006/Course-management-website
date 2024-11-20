package com.asaki0019.website.api.user;

import com.asaki0019.website.repository.UserRepository;
import com.asaki0019.website.service.UserServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/user/login")
public class LoginServlet extends HttpServlet {

    private final UserServiceImpl userServiceImpl;

    // 使用依赖注入
    public LoginServlet(UserRepository userRepository) {
        this.userServiceImpl = new UserServiceImpl(userRepository);
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

    private String readJsonBody(BufferedReader reader) throws IOException {
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        return jsonBody.toString();
    }

    private void writeErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        writeResponse(createErrorJson(), response.getWriter());
    }

    private JSONObject createErrorJson() {
        return new JSONObject()
                .put("error_code", "3")
                .put("message", "An error occurred while processing the request.");
    }

    private void writeResponse(JSONObject json, PrintWriter out) {
        try {
            out.print(json.toString());
        } finally {
            out.flush();
        }
    }
}