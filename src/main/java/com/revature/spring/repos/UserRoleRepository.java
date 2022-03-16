package com.revature.spring.repos;


import com.revature.spring.models.UserRole;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, String> {

    UserRole getUserRoleById(String id);

    UserRole getUserRoleByRole(String role);

    @Query("from UserRole ur where ur.id = ?1")
    List<UserRole> getUserRoleByID(String id);
}
