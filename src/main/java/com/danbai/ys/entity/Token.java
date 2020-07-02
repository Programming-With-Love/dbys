package com.danbai.ys.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author danbai
 * @date 2019-11-21 14:21
 */
@ApiModel(value = "token", description = "token")
public class Token implements Serializable {
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "token")
    private String token;
    public static final String TOKEN="token_";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Token(String username, String token) {
        this.username = username;
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public Token() {
    }
}
