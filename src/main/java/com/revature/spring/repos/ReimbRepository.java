package com.revature.spring.repos;


import com.revature.spring.models.Reimbursement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbRepository extends CrudRepository<Reimbursement, String> {

    Reimbursement getReimbById(String id);

    Reimbursement getReimbByAuthor(String id);

    List<Reimbursement> getAllByAuthor(String author_user);

    List<Reimbursement> getAllByStatus(String status);

    List<Reimbursement> getAllByType(String type);

    List<Reimbursement> getAllByResolver_id(String resolver_user);



}
