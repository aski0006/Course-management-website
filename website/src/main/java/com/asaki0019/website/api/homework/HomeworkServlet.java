package com.asaki0019.website.api.homework;

import com.asaki0019.website.repository.HomeworkRepository;
import com.asaki0019.website.repository.HomeworkRepositoryImpl;
import com.asaki0019.website.service.HomeworkService;
import com.asaki0019.website.service.HomeworkServiceImpl;
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

@WebServlet("/api/homework/display")
public class HomeworkServlet extends HttpServlet {

    private HomeworkService homeworkService;

    public void init() throws ServletException {
        super.init();
        homeworkService = new HomeworkServiceImpl(new HomeworkRepositoryImpl());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter();
             BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(request.getInputStream()))) {
            JSONObject requestBody = new JSONObject(readJsonBody(reader));
            JSONObject result = homeworkService.display(request, requestBody);
            ;
            response.setStatus(HttpServletResponse.SC_OK);
            writeResponse(result, out);
        } catch (Exception e) {
            writeErrorResponse(response);
        }
    }

}