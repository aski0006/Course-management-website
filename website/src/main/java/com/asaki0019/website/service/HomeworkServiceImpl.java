package com.asaki0019.website.service;

import com.asaki0019.website.models.Homework;
import com.asaki0019.website.repository.HomeworkRepository;
import com.asaki0019.website.tools.JwtTool;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        List<Homework> currentHomework = new ArrayList<>();
        List<Homework> overdueHomework = new ArrayList<>();
        List<Homework> completedHomework = new ArrayList<>();
        List<Homework> unpublishedHomework = new ArrayList<>();
        for (Homework homework : homeworks) {
            switch (homework.getStatus(LocalDateTime.now())) {
                case CURRENT:
                    currentHomework.add(homework);
                    break;
                case OVERDUE:
                    overdueHomework.add(homework);
                    break;
                case COMPLETED:
                    completedHomework.add(homework);
                    break;
                case UNPUBLISHED:
                    unpublishedHomework.add(homework);
                    break;
            }
        }

        JSONObject response = new JSONObject();
        response.put("error_code",0);
        response.put("token", token);
        response.put("currentHomework", currentHomework);
        response.put("overdueHomework", overdueHomework);
        response.put("completedHomework", completedHomework);
        response.put("unpublishedHomework", unpublishedHomework);
        return response;
    }
}
