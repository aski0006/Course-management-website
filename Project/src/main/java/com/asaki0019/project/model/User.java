/**
 * 用户实体类，对应数据库中的users表。
 */
package com.asaki0019.project.model;

import java.util.Date;
import java.util.Objects;

/**
 * 用户实体类，封装了用户的基本信息。
 */
public class User {
    /**
     * 用户的唯一标识符，对应数据库中的id字段。
     */
    private long id;

    /**
     * 用户名，对应数据库中的username字段，不可为空且唯一。
     */
    private String username;

    /**
     * 用户密码，对应数据库中的password字段，不可为空。
     * 通常需要更长的字符串来存储加密后的密码。
     */
    private String password;

    /**
     * 用户的全名，对应数据库中的name字段，不可为空。
     */
    private String name;

    /**
     * 用户的角色，对应数据库中的role字段，不可为空。
     */
    private String role;

    /**
     * 用户的创建时间，对应数据库中的created_time字段，不可为空。
     */
    private Date createdTime;

    /**
     * 用户的更新时间，对应数据库中的updated_time字段，不可为空。
     */
    private Date updatedTime;

    /**
     * 无参构造函数。
     */
    public User() {
    }

    /**
     * 全参构造函数，初始化用户信息。
     *
     * @param id 用户ID
     * @param username 用户名
     * @param password 用户密码
     * @param name 用户全名
     * @param role 用户角色
     */
    public User(long id, String username, String password, String name, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.createdTime = new Date();
        this.updatedTime = new Date();
    }

    /**
     * 获取用户ID。
     *
     * @return 用户ID
     */
    public long getId() {
        return id;
    }

    /**
     * 设置用户ID。
     *
     * @param id 用户ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 获取用户名。
     *
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名。
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户密码。
     *
     * @return 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码。
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户全名。
     *
     * @return 用户全名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户全名。
     *
     * @param name 用户全名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取用户角色。
     *
     * @return 用户角色
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置用户角色。
     *
     * @param role 用户角色
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 获取用户创建时间。
     *
     * @return 用户创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置用户创建时间。
     *
     * @param createdTime 用户创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取用户更新时间。
     *
     * @return 用户更新时间
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 设置用户更新时间。
     *
     * @param updatedTime 用户更新时间
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * 重写equals方法，用于比较两个User对象是否相等。
     *
     * @param o 要比较的对象
     * @return 如果相等返回true，否则返回false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(role, user.role) && Objects.equals(createdTime, user.createdTime) && Objects.equals(updatedTime, user.updatedTime);
    }

    /**
     * 重写hashCode方法，用于计算User对象的哈希值。
     *
     * @return User对象的哈希值
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, name, role, createdTime, updatedTime);
    }
}