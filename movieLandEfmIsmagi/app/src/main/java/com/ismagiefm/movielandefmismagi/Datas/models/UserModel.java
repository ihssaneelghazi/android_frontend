package com.ismagiefm.movielandefmismagi.Datas.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserModel {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("roles")
    private List<String> roles;

    public UserModel(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}