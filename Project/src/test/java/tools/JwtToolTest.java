package tools;

import com.asaki0019.project.tools.JwtTool;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtToolTest {

    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_ROLE = "testRole";

    @BeforeEach
    void setUp() {
        // 使用一个固定的密钥以确保测试的一致性
        JwtTool.setSecretKey("testSecretKey");
    }

    @Test
    void testGenerateToken() {
        String token = JwtTool.generateToken(TEST_USERNAME, TEST_ROLE);
        assertNotNull(token, "Generated token should not be null");
        assertNotEquals("", token, "Generated token should not be empty");
    }

    @Test
    void testExtractUsername() {
        String token = JwtTool.generateToken(TEST_USERNAME, TEST_ROLE);
        String extractedUsername = JwtTool.extractUsername(token);
        assertEquals(TEST_USERNAME, extractedUsername, "Extracted username should match the one used to generate the token");
    }

    @Test
    void testExtractRole() {
        String token = JwtTool.generateToken(TEST_USERNAME, TEST_ROLE);
        String extractedRole = JwtTool.extractRole(token);
        assertEquals(TEST_ROLE, extractedRole, "Extracted role should match the one used to generate the token");
    }

    @Test
    void testIsTokenExpired() {
        // Generate a token with past expiration date
        String expiredToken = Jwts.builder()
                .setSubject(TEST_USERNAME)
                .claim("role", TEST_ROLE)
                .setIssuedAt(new Date(System.currentTimeMillis() - 7200000)) // Issued 2 hours ago
                .setExpiration(new Date(System.currentTimeMillis() - 3600000)) // Expired 1 hour ago
                .signWith(SignatureAlgorithm.HS256, JwtTool.getSecretKey())
                .compact();

        // Check if the token is expired
        assertTrue(JwtTool.isTokenExpired(expiredToken), "Token should be expired");
    }

    @Test
    void testIsTokenNotExpired() {
        // Generate a token that will expire in 1 hour
        String token = JwtTool.generateToken(TEST_USERNAME, TEST_ROLE);

        // Check if the token is not expired
        assertFalse(JwtTool.isTokenExpired(token), "Token should not be expired");
    }
}