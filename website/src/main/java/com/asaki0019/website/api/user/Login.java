package com.asaki0019.website.api.user;

import com.asaki0019.website.repository.UserRepository;
import com.asaki0019.website.repository.UserRepositoryImpl;
import com.asaki0019.website.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/user/login")
public class Login extends HttpServlet {

    UserRepository userRepository = new UserRepositoryImpl();
    private final UserService userService = new UserService(userRepository);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(request.getInputStream()));
            StringBuilder jsonBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }

            JSONObject requestBody = new JSONObject(jsonBody.toString());
            JSONObject result = userService.login(request, requestBody);

            writeResponse(out, HttpServletResponse.SC_OK, result, response);
        } catch (Exception e) {
            writeErrorResponse(out, response);
        }
    }

    private void writeErrorResponse(PrintWriter out, HttpServletResponse response) {
        JSONObject json = new JSONObject()
                .put("error_code", "3")
                .put("message", "An error occurred while processing the request.");
        writeResponse(out, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, json, response);
    }

    private void writeResponse(PrintWriter out, int statusCode, JSONObject json, HttpServletResponse response) {
        response.setStatus(statusCode);
        out.print(json.toString());
        out.flush();
    }
}