package com.revature.spring.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ERS_REIMBURSEMENT_STATUSES")
public class ReimbursementStatus {

    @Id
    @Column(name = "STATUS_ID")
    private String id;

    @Column(unique = true)
    private String status;

    public ReimbursementStatus() {
        super();
    }

    public ReimbursementStatus(String status_id, String status) {
        this.id = status_id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
