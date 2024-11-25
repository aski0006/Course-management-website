package com.asaki0019.website.repository;

import com.asaki0019.website.models.Homework;

import java.util.List;

public interface HomeworkRepository {
    Homework findById(int id);
    void save(Homework homework);
    void update(Homework homework);
    void delete(int id);
    List<Homework> findByStudentId(String studentId);
}
