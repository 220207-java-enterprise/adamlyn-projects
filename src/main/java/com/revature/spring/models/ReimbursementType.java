package com.revature.spring.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;;

@Entity
@Table(name = "ERS_REIMBURSEMENT_TYPES")
public class ReimbursementType {

    @Id

    @Column(name = "TYPE_ID")
    private String id;

    @Column(unique = true)
    private String type;

    public ReimbursementType() {
        super();
    }

    public ReimbursementType(String type_id, String type) {
        this.id = type_id;
        this.type = type;
    }

    public String getType_id() {
        return id;
    }

    public void setType_id(String type_id) {
        this.id = type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
