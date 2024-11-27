package com.asaki0019.website.repository;

import com.asaki0019.website.models.Homework;
import com.asaki0019.website.tools.DatabaseUtils;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeworkRepositoryImpl implements HomeworkRepository {
    private static final Logger logger = Logger.getLogger(HomeworkRepositoryImpl.class.getName());

    private static  BasicDataSource dataSource;

    public HomeworkRepositoryImpl(){
        dataSource = new DatabaseUtils().getDataSource();
    }
    @Override
    public Homework findById(int id) {
        String query = "SELECT * FROM 作业 WHERE 作业id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Homework(
                        resultSet.getInt("作业id"),
                        resultSet.getDouble("分数"),
                        resultSet.getString("作业内容"),
                        resultSet.getTimestamp("创建时间").toLocalDateTime(),
                        resultSet.getTimestamp("截止时间").toLocalDateTime(),
                        resultSet.getTimestamp("更新时间").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while finding homework by id", e);
        }
        return null;
    }

    @Override
    public void save(Homework homework) {
        String query = "INSERT INTO 作业 (作业id,分数, 作业内容, 创建时间, 截止时间, 更新时间) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, homework.getId());
            preparedStatement.setDouble(2, homework.getScore());
            preparedStatement.setString(3, homework.getContent());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(homework.getCreateTime()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(homework.getEndTime()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(homework.getUpdateTime()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while saving homework", e);
        }
    }

    @Override
    public void update(Homework homework) {
        String query = "UPDATE 作业 SET 分数 = ?, 作业内容  = ?, 创建时间  = ?, 截止时间  = ?, 更新时间  = ? WHERE 作业id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, homework.getScore());
            preparedStatement.setString(2, homework.getContent());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(homework.getCreateTime()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(homework.getEndTime()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(homework.getUpdateTime()));
            preparedStatement.setInt(6, homework.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while updating homework", e);
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM 作业 WHERE 作业id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while deleting homework", e);
        }
    }

    @Override
    public List<Homework> findByStudentId(String studentId) {
        String query = "SELECT * FROM 用户_作业 WHERE 账号 = ?";
        List<Homework> homeworks = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                homeworks.add(findById(resultSet.getInt("作业id")));
            }
            return homeworks;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while finding homework by id", e);
        }
        return null;
    }
}
