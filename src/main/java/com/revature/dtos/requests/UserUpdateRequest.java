package com.revature.dtos.requests;

import com.revature.models.UserRole;

import java.util.Objects;

public class UserUpdateRequest {
        private String user_id;
        private String given_name;
        private String surname;
        private String email;
        private String username;
        private String password;
        private Boolean isActive;

        private String role;

        public UserUpdateRequest() {
            super(); // not required, but it bugs me personally not to have it
        }

        public UserUpdateRequest(String given_name, String lastName, String email, String username, String password) {
            this.given_name = given_name;
            this.surname = lastName;
            this.email = email;
            this.username = username;
            this.password = password;
        }

        public UserUpdateRequest(String given_name, String surname, String email, String username,
                                 String password, String role) {
            this.given_name = given_name;
            this.surname = surname;
            this.email = email;
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public UserUpdateRequest(String given_name, String surname, String email, String username, Boolean isActive) {
            this.given_name = given_name;
            this.surname = surname;
            this.email = email;
            this.username = username;
            this.isActive = isActive;
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

        public String toFileString() {
            return new StringBuilder(user_id).append(":")
                    .append(given_name).append(":")
                    .append(surname).append(":")
                    .append(email).append(":")
                    .append(username).append(":")
                    .append(password).toString();
        }


        @Override
        public int hashCode() {
            return Objects.hash(user_id, given_name, surname, email, username, password, role);
        }

        @Override
        public String toString() {
            return "User{" +
                    "user_id='" + user_id + '\'' +
                    ", given_name='" + given_name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", email='" + email + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", isActive=" + isActive +
                    ", role=" + role +
                    '}';
        }
}
