package com.revature.dtos.responses;

import com.revature.models.User;

public class UserResponse {
    private String user_id;
    private String given_name;
    private String surname;
    private String email;
    private String username;
    private Boolean isActive;
    private String role;

    public UserResponse(){
        super();
    }

    public UserResponse(User user) {
        this.user_id = user.getUser_id();
        this.given_name = user.getGiven_name();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.isActive = user.isActive();
        this.role = user.getRole().getRole();
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

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "user_id='" + user_id + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", isActive=" + isActive +
                ", role='" + role + '\'' +
                '}';
    }
}
