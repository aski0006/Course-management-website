package com.asaki0019.website.api.user;

import com.asaki0019.website.repository.UserRepository;
import com.asaki0019.website.repository.UserRepositoryImpl;
import com.asaki0019.website.service.UserServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/user/register")
public class RegisterServlet extends HttpServlet {

    private final UserRepository userRepository = new UserRepositoryImpl();
    private final UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            JSONObject requestBody = readRequestBody(request);
            JSONObject result = userServiceImpl.register(request, requestBody);
            response.setStatus(HttpServletResponse.SC_OK);
            writeResponse(result, out);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writeErrorResponse(out);
        }
    }

    private JSONObject readRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(request.getInputStream()));
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        return new JSONObject(jsonBody.toString());
    }

    private void writeErrorResponse(PrintWriter out) {
        JSONObject json = new JSONObject()
                .put("error_code", "3")
                .put("message", "请求处理过程中发生错误");
        writeResponse(json, out);
    }

    private void writeResponse(JSONObject json, PrintWriter out) {
        try {
            out.print(json.toString());
        } finally {
            out.flush();
        }
    }
}