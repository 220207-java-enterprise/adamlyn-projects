package com.revature.spring.models;


import javax.persistence.*;

@Entity
@Table(name = "ERS_REIMBURSEMENTS")
public class Reimbursement {

    @Id
    @Column(name="REIMB_ID")
    private String id;

    @Column(nullable = false)
    private Float amount;

    @Column(nullable = false)
    private String submitted;

    @Column
    private String resolved;

    @Column(nullable = false)
    private String description;

    @Column
    private String receipt;

    @Column
    private String payment_id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "resolver_id")
    private User resolver;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private ReimbursementStatus status;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ReimbursementType type;

    public Reimbursement() {
        super();
    }

    public Reimbursement(Float amount, String submitted, String description, String receipt) {
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.receipt = receipt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public User getAuthor_id() {
        return author;
    }

    public void setAuthor_id(User author_id) {
        this.author = author_id;
    }

    public User getResolver_id() {
        return resolver;
    }

    public void setResolver_id(User resolver_id) {
        this.resolver = resolver_id;
    }

    public ReimbursementStatus getStatus_id() {
        return status;
    }

    public void setStatus_id(ReimbursementStatus status_id) {
        this.status = status_id;
    }

    public ReimbursementType getType_id() {
        return type;
    }

    public void setType_id(ReimbursementType type_id) {
        this.type = type_id;
    }
}
