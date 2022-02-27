package com.revature.models;

import java.util.Objects;

public class User {
    private String user_id;
    private String given_name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private boolean isActive;

    private UserRole role;

    // TODO create a Role enum

    public User() {
        super(); // not required, but it bugs me personally not to have it
    }

    public User(String given_name, String lastName, String email, String username, String password) {
        this.given_name = given_name;
        this.surname = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String toFileString() {
        return new StringBuilder(user_id).append(":")
                .append(given_name).append(":")
                .append(surname).append(":")
                .append(email).append(":")
                .append(username).append(":")
                .append(password).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User User = (User) o;
        return Objects.equals(user_id, User.user_id)
                && Objects.equals(given_name, User.given_name)
                && Objects.equals(surname, User.surname)
                && Objects.equals(email, User.email)
                && Objects.equals(username, User.username)
                && Objects.equals(password, User.password)
                && Objects.equals(role, User.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, given_name, surname, email, username, password, role);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id='" + user_id + '\'' +
                ", given_name ='" + given_name + '\'' +
                ", surname ='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", password='" + role +
                '}';
    }

}
