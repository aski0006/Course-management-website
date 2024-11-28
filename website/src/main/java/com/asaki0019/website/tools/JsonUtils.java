package com.asaki0019.website.tools;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonUtils {
    static public String readJsonBody(BufferedReader reader) throws IOException {
        StringBuilder jsonBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }
        return jsonBody.toString();
    }

    static public void writeErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        writeResponse(createErrorJson(), response.getWriter());
    }

    static public JSONObject createErrorJson() {
        return new JSONObject()
                .put("error_code", "3")
                .put("message", "An error occurred while processing the request.");
    }

    static public void writeResponse(JSONObject json, PrintWriter out) {
        try {
            out.print(json.toString());
        } finally {
            out.flush();
        }
    }
}
