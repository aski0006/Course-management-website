package com.asaki0019.project.api.tools;

import com.asaki0019.project.tools.HutoolCaptcha;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/api/tools/captcha")
public class CaptchaServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CaptchaServlet.class.getName());
    private HutoolCaptcha captchaGenerator;

    @Override
    public void init() throws ServletException {
        super.init();
        captchaGenerator = new HutoolCaptcha();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        resp.setContentType("image/png");
        byte[] captchaBytes = captchaGenerator.generateCaptcha();
        if (captchaBytes != null) {
            resp.getOutputStream().write(captchaBytes);
            req.getSession().setAttribute("captchaCode", captchaGenerator.getCaptchaCode());
        } else {
            logger.log(Level.SEVERE, "Failed to generate captcha image.");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to generate captcha image.");
        }
    }
}