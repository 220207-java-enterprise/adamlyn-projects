package com.revature.spring.repos;

import com.revature.spring.models.ReimbursementStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbStatusRepository extends CrudRepository<ReimbursementStatus, String> {

    ReimbursementStatus getReimbByid(String id);

    ReimbursementStatus getReimbByStatus(String status);
}
