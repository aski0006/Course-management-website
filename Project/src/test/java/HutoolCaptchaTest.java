import com.asaki0019.project.tools.HutoolCaptcha;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * HutoolCaptcha 工具类的测试类
 */
public class HutoolCaptchaTest {

    private HutoolCaptcha hutoolCaptcha;

    @BeforeEach
    void setUp() {
        // 在每个测试方法执行前初始化 HutoolCaptcha 实例
        hutoolCaptcha = new HutoolCaptcha();
    }

    @Test
    void testGenerateCaptcha() {
        // 生成验证码图片并获取其字节数组
        byte[] captchaImage = hutoolCaptcha.generateCaptcha();

        // 确保生成的字节数组不为空
        Assertions.assertNotNull(captchaImage);
        Assertions.assertTrue(captchaImage.length > 0);
    }

    @Test
    void testGetCaptchaCode() {
        // 生成验证码图片
        byte[] captchaImage = hutoolCaptcha.generateCaptcha();
        Assertions.assertNotNull(captchaImage);

        // 获取验证码内容
        String captchaCode = hutoolCaptcha.getCaptchaCode();
        // 确保验证码内容不为空且是6位数字
        Assertions.assertNotNull(captchaCode);
        Assertions.assertEquals(6, captchaCode.length());
        Assertions.assertTrue(captchaCode.matches("\\d{6}"));
    }

    @Test
    void testGetCaptchaCodeWithoutGenerating() {
        // 尝试获取验证码内容，但未生成验证码
        String captchaCode = hutoolCaptcha.getCaptchaCode();

        // 确保返回值为 null
        Assertions.assertNull(captchaCode);
    }
}