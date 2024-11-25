package com.asaki0019.website.tools;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 生成验证码图片的工具类
 *
 * @author asaki0019
 * @version 1.0
 */
public class HutoolCaptcha {
    private static final Logger logger = Logger.getLogger(HutoolCaptcha.class.getName());
    private LineCaptcha lineCaptcha;

    /**
     * 生成验证码图片并返回其字节数组
     *
     * @return 验证码图片的字节数组
     */
    public byte[] generateCaptcha() {
        try {
            // 创建一个 200x100 像素的验证码
            lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

            // 设置验证码的内容（6 位数字）
            RandomGenerator randomGenerator = new RandomGenerator("0123456789", 6);
            lineCaptcha.setGenerator(randomGenerator);

            // 生成验证码图片
            lineCaptcha.createCode();

            // 将验证码图片写入字节数组输出流
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                lineCaptcha.write(out);
                return out.toByteArray();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "生成验证码时发生IO异常", e);
            return null; // 或者抛出异常
        }
    }

    /**
     * 获取生成的验证码内容
     *
     * @return 验证码内容，如果未生成验证码则返回 null
     */
    public String getCaptchaCode() {
        if (lineCaptcha == null) {
            logger.log(Level.WARNING, "尝试获取验证码内容，但未生成验证码");
            return null;
        }
        return lineCaptcha.getCode();
    }
}