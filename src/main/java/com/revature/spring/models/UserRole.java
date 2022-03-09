package com.revature.spring.models;


import javax.persistence.*;

@Entity
@Table(name = "ERS_USER_ROLES")
public class UserRole {

    //TODO ???
    @OneToMany
    @Id
    private String role_id;

    @Column(unique = true)
    private String role;

    public UserRole() {
        super();
    }

    public UserRole(String role_id, String role) {
        this.role_id = role_id;
        this.role = role;
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
}
