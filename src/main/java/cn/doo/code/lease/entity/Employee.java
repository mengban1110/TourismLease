package cn.doo.code.lease.entity;

import java.util.Objects;

public class Employee {
    private int id;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer level;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Objects.equals(username, employee.username) && Objects.equals(password, employee.password) && Objects.equals(email, employee.email) && Objects.equals(avatar, employee.avatar) && Objects.equals(level, employee.level) && Objects.equals(token, employee.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, avatar, level, token);
    }
}
