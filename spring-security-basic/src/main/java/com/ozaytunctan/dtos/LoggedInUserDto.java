package com.ozaytunctan.dtos;

import java.io.Serializable;
import java.util.List;

public class LoggedInUserDto implements Serializable {

    private Long id;
    private String userName;
    private String password;
    private List<String> roles;
    private String token;

    public LoggedInUserDto() {
    }

    public LoggedInUserDto(Long id, String userName, String password, List<String> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public LoggedInUserDto(String userName, String password, List<String > roles) {
        this(0l, userName, password, roles);
    }

    public LoggedInUserDto(String userName, String password) {
        this(userName, password, null);
    }

    public static LoggedInUserDto of(String username, String password) {
        return new LoggedInUserDto(username, password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public LoggedInUserDto token(String token) {
        this.token = token;
        return this;
    }

    public LoggedInUserDto roles(List<String> roles) {
        this.roles = roles;
        return this;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
