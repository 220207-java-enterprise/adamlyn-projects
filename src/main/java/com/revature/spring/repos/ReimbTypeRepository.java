package com.revature.spring.repos;


import com.revature.spring.models.ReimbursementType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbTypeRepository extends CrudRepository<ReimbursementType, String> {

    ReimbursementType getReimbByid(String id);

    ReimbursementType getReimbByType(String type);
}
