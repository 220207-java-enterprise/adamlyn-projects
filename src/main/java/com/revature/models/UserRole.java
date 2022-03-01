package com.revature.models;

import java.util.Objects;

public class UserRole {

    private String role_id;
    private String role;

    public UserRole(){super();}

    public UserRole(String roleId, String Role){
        this.role_id = roleId;
        this.role = Role;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(role_id, userRole.role_id) && Objects.equals(role, userRole.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role_id, role);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "role_id='" + role_id + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public void getById(UserRole role) {
    }
}

