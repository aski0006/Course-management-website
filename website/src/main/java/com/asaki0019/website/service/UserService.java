package com.asaki0019.website.service;

import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;

public interface UserService {
    JSONObject login(HttpServletRequest request, JSONObject requestBody);
    JSONObject register(HttpServletRequest request, JSONObject requestBody);
}
