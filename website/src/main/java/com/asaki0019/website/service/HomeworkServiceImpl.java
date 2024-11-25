package com.asaki0019.website.service;

import com.asaki0019.website.models.Homework;
import com.asaki0019.website.repository.HomeworkRepository;
import com.asaki0019.website.tools.JwtTool;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;

import java.util.List;

public class HomeworkServiceImpl implements HomeworkService{

    private final HomeworkRepository homeworkRepository;
    public HomeworkServiceImpl(HomeworkRepository homeworkRepository) {
        this.homeworkRepository = homeworkRepository;
    }
    @Override
    public JSONObject display(HttpServletRequest request, JSONObject requestBody) {
        String token = requestBody.getString("token");
        String account = JwtTool.extractUsername(token);
        List<Homework> homeworks = homeworkRepository.findByStudentId(account);
        JSONObject response = new JSONObject();
        response.put("token",homeworks);
        return response;
    }
}
