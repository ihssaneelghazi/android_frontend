package com.ismagiefm.movielandefmismagi.Datas.models;

import java.util.HashMap;
import java.util.Map;

public class User {
    private Long id;
    private String username;
    private String email;
    private String password;

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Map<String, String> toMap() {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);

        return params;
    }

    // Ajoutez des getters et des setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}