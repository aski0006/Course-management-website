package com.asaki0019.website.service;

import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;

public interface HomeworkService {
    JSONObject display(HttpServletRequest request, JSONObject requestBody);
}
