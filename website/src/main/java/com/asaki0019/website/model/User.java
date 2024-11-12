package com.asaki0019.website.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户类，用于表示系统中的用户信息。
 */
public class User {
    /**
     * 用户名，唯一标识一个用户。
     */
    private String account;

    /**
     * 用户密码，用于登录验证。
     */
    private String password;

    /**
     * 用户真实姓名。
     */
    private String name;

    /**
     * 用户角色，用于权限管理。
     */
    private String role;

    /**
     * 用户创建时间。
     */
    private LocalDateTime createdTime;

    /**
     * 用户信息最后更新时间。
     */
    private LocalDateTime updatedTime;

    /**
     * 默认构造方法。
     */
    public User() {
    }

    /**
     * 带参数的构造方法，用于初始化用户信息。
     *
     * @param account 用户名
     * @param password 用户密码
     * @param name     用户真实姓名
     * @param role     用户角色
     */
    public User(String account, String password, String name, String role) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.role = role;
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }

    // Getters and Setters

    /**
     * 获取用户名。
     *
     * @return 用户名
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置用户名。
     *
     * @param account 用户名
     */
    public void setAccount(String account) {
        this.account = account;
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
     * 获取用户的真实姓名。
     *
     * @return 用户的真实姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置用户的真实姓名。
     *
     * @param name 用户的真实姓名
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
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置用户创建时间。
     *
     * @param createdTime 用户创建时间
     */
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取用户信息最后更新时间。
     *
     * @return 用户信息最后更新时间
     */
    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 私有方法，用于设置用户信息最后更新时间。
     *
     * @param updatedTime 用户信息最后更新时间
     */
    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * 重写 equals 方法，用于比较两个用户对象是否相等。
     *
     * @param o 要比较的对象
     * @return 如果对象相等返回 true，否则返回 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(account, user.account) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && Objects.equals(role, user.role) && Objects.equals(createdTime, user.createdTime) && Objects.equals(updatedTime, user.updatedTime);
    }

    /**
     * 重写 hashCode 方法，用于计算用户对象的哈希码。
     *
     * @return 用户对象的哈希码
     */
    @Override
    public int hashCode() {
        return Objects.hash(account, password, name, role, createdTime, updatedTime);
    }
}