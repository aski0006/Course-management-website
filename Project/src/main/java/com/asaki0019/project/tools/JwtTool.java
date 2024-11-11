package com.asaki0019.project.tools;

import io.jsonwebtoken.*;

import java.util.Base64;
import java.util.Date;

/**
 * JwtTool 类是一个用于生成、解析和管理 JWT（JSON Web Token）的工具类。
 * 该类提供了生成令牌、提取令牌信息、验证令牌是否过期等功能。
 *
 * @author asaki0019
 * @version 1.0
 * @since 2024-11-11
 */
public class JwtTool {

    /**
     * 用于签名和验证 JWT 的密钥。初始值为 "123456" 的 Base64 编码。
     */
    private static String SECRET_KEY = Base64.getEncoder().encodeToString("123456".getBytes());

    /**
     * 设置用于签名和验证 JWT 的密钥。
     *
     * @param secretKey 新的密钥字符串
     */
    public static void setSecretKey(String secretKey) {
        SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * 生成一个 JWT 令牌。
     *
     * @param username 用户名
     * @param role     用户角色
     * @return 生成的 JWT 令牌
     */
    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 令牌有效期1小时
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * 解析 JWT 令牌，并返回其中的 Claims 对象。
     *
     * @param token JWT 令牌
     * @return 解析出的 Claims 对象
     * @throws JwtException 如果解析过程中出现问题
     */
    public static Claims getClaims(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从 JWT 令牌中提取用户名。
     *
     * @param token JWT 令牌
     * @return 提取出的用户名
     */
    public static String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * 从 JWT 令牌中提取用户角色。
     *
     * @param token JWT 令牌
     * @return 提取出的用户角色
     */
    public static String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    /**
     * 检查 JWT 令牌是否已过期。
     *
     * @param token JWT 令牌
     * @return 如果令牌已过期返回 true，否则返回 false
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * 获取当前用于签名和验证 JWT 的密钥。
     *
     * @return 当前密钥的 Base64 编码字符串
     */
    public static String getSecretKey() {
        return SECRET_KEY;
    }
}