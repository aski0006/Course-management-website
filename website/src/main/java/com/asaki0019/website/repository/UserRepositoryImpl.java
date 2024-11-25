package com.asaki0019.website.repository;

import com.asaki0019.website.models.User;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepositoryImpl implements UserRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/coursewebsitedatabase";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static final Logger logger = Logger.getLogger(UserRepositoryImpl.class.getName());

    private static final BasicDataSource dataSource;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "MySQL JDBC driver not found", e);
        }
        dataSource = new BasicDataSource();
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(10);
    }

    @Override
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
            logger.log(Level.SEVERE, "Error occurred while finding user by account: " + account, e);
        }
        return null;
    }

    @Override
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

    @Override
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

    @Override
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
}