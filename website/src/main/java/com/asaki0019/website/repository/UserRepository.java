package com.asaki0019.website.repository;

import com.asaki0019.website.model.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 用户数据仓库类，负责与数据库的交互，包括用户的查询、保存、更新和删除操作。
 */
public class UserRepository {

    // 数据库连接URL
    private static final String URL = "jdbc:mysql://localhost:3306/coursewebsitedatabase";
    // 数据库用户名
    private static final String USER = "root";
    // 数据库密码
    private static final String PASSWORD = "root";

    // 日志记录器
    private static final Logger logger = Logger.getLogger(UserRepository.class.getName());

    // 数据源，管理数据库连接池
    private static final BasicDataSource dataSource;

    // 静态初始化块，初始化数据源
    static {
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setInitialSize(5); // 初始连接数
        dataSource.setMaxTotal(10);   // 最大连接数
    }

    /**
     * 根据用户名查找用户
     *
     * @param account 用户名
     * @return User对象，如果找不到返回null
     */
    public User findByUsername(String account) {
        String sql = "SELECT * FROM users WHERE account = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while finding user by username: " + account, e);
        }
        return null;
    }

    /**
     * 保存新用户到数据库
     *
     * @param user 要保存的用户对象
     */
    public void save(User user) {
        String sql = "INSERT INTO users (account, password, name, role, created_time, updated_time) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getAccount());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setString(4, user.getRole());
            stmt.setTimestamp(5, Timestamp.valueOf(user.getCreatedTime()));
            stmt.setTimestamp(6, Timestamp.valueOf(user.getUpdatedTime()));
            stmt.executeUpdate();
            logger.log(Level.INFO, "User saved successfully: " + user);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while saving user: " + user, e);
        }
    }

    /**
     * 更新数据库中的用户信息
     *
     * @param user 要更新的用户对象
     */
    public void update(User user) {
        String sql = "UPDATE users SET password = ?, name = ?, role = ?, updated_time = ? WHERE account = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getRole());
            stmt.setTimestamp(4, Timestamp.valueOf(user.getUpdatedTime()));
            stmt.setString(5, user.getAccount());
            stmt.executeUpdate();
            logger.log(Level.INFO, "User updated successfully: " + user);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while updating user: " + user, e);
        }
    }

    /**
     * 根据用户名删除用户
     *
     * @param account 要删除的用户账号
     */
    public void delete(String account) {
        String sql = "DELETE FROM users WHERE account = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account);
            stmt.executeUpdate();
            logger.log(Level.INFO, "User deleted successfully, account: " + account);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error occurred while deleting user by account: " + account, e);
        }
    }

    /**
     * 将数据库查询结果映射到User对象
     *
     * @param rs 数据库查询结果集
     * @return User对象
     * @throws SQLException 如果映射过程中发生SQL错误
     */
    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setAccount(rs.getString("account"));
        user.setPassword(rs.getString("password"));
        user.setName(rs.getString("name"));
        user.setRole(rs.getString("role"));
        user.setCreatedTime(rs.getTimestamp("created_time").toLocalDateTime());
        user.setUpdatedTime(rs.getTimestamp("updated_time").toLocalDateTime());
        return user;
    }

    public BasicDataSource getDataSource() {
        return dataSource;
    }
}