package com.danbai.ys.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户类
 *
 * @author danbai
 * @date 2019/10/13
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int GENERAL = 2;
    public static final int ADMIN = 2;
    public static final int BAN = 0;
    public static final String DEFAULT_USER = "user";
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型 0 禁用 1普通 2 管理员
     */
    @Column(name = "user_type")
    private Integer userType;

    /**
     * 用户密码
     */
    private String password;

    private String email;

    private String headurl;

    /**
     * 获取用户id
     *
     * @return id - 用户id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置用户id
     *
     * @param id 用户id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取用户类型 0 禁用 1普通 2 管理员
     *
     * @return user_type - 用户类型 0 禁用 1普通 2 管理员
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置用户类型 0 禁用 1普通 2 管理员
     *
     * @param userType 用户类型 0 禁用 1普通 2 管理员
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return headurl
     */
    public String getHeadurl() {
        return headurl;
    }

    /**
     * @param headurl
     */
    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userType=" + userType +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", headurl='" + headurl + '\'' +
                '}';
    }
}