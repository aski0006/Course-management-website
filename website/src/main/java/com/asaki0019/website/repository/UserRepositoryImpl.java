package com.asaki0019.website.repository;

import com.asaki0019.website.models.User;
import com.asaki0019.website.tools.DatabaseUtils;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = Logger.getLogger(UserRepositoryImpl.class.getName());

    private static BasicDataSource dataSource;

    public UserRepositoryImpl() {
        dataSource = new DatabaseUtils().getDataSource();
    }

    @Override
    public User findByAccount(String account) {
        String query = "SELECT * FROM 用户 WHERE 账号 = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, account);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getString("账号"),
                        resultSet.getString("密码"),
                        resultSet.getString("姓名"),
                        resultSet.getString("角色"),
                        resultSet.getTimestamp("创建时间").toLocalDateTime(),
                        resultSet.getTimestamp("更新时间").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while finding user by account", e);
        }
        return null;
    }

    @Override
    public void save(User user) {
        String query = "INSERT INTO 用户 (账号, 密码, 姓名, 角色) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getAccount());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getRole());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while saving user", e);
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE 用户 SET 密码 = ?, 姓名 = ?, 角色 = ?, 更新时间 = CURRENT_TIMESTAMP WHERE 账号 = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setString(4, user.getAccount());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while updating user", e);
        }
    }

    @Override
    public void delete(String account) {
        String query = "DELETE FROM 用户 WHERE 账号 = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, account);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while deleting user", e);
        }
    }
}