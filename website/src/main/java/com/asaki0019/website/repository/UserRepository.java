package com.asaki0019.website.repository;

import com.asaki0019.website.models.User;

public interface UserRepository {
    User findByAccount(String account);
    void save(User user);
    void update(User user);
    void delete(String account);
}