package com.revature.spring.models;


import javax.persistence.*;

@Entity
@Table(name = "ERS_USER_ROLES")
public class UserRole {

    @Id
    @Column(name = "ROLE_ID")
    private String id;

    @Column(unique = true)
    private String role;

    public UserRole() {
        super();
    }

    public UserRole(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
