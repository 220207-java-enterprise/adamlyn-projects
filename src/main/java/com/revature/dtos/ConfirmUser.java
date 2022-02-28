package com.revature.dtos;

import com.revature.models.User;

public class ConfirmUser {
    private String user_id;
    private String given_name;
    private String surname;
    private String email;
    private String username;
    private boolean isActive;

    private String role;

    public ConfirmUser(){super();}

    public ConfirmUser(String user_id, String given_name, String surname, String email, String username, boolean isActive, String role_id) {
        this.user_id = user_id;
        this.given_name = given_name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.isActive = isActive;
        this.role = role_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User extractUser(){
        return new User(user_id, given_name, surname, email, username);
    }

    @Override
    public String toString() {
        return "ConfirmUser{" +
                "user_id='" + user_id + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", isActive=" + isActive +
                ", role=" + role +
                '}';
    }
}
