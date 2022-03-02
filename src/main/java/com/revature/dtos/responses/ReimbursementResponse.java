package com.revature.dtos.responses;

import com.revature.models.Reimbursement;

public class ReimbursementResponse {
    private String reimb_id;
    private float amount;
    private String submitted;
    private String resolved;
    private String description;
    private String receipt;
    private String payment_id;
    private String author_id;
    private String resolver_id;
    private String status_id;
    private String type_id;

    public ReimbursementResponse() {super();
    }

    public ReimbursementResponse(Reimbursement rmb) {
        this.reimb_id = rmb.getReimb_id();
        this.amount = rmb.getAmount();
        this.submitted = rmb.getSubmitted();
        this.resolved = rmb.getResolved();
        this.description = rmb.getDescription();
        this.receipt = rmb.getReceipt();
        this.payment_id = rmb.getPayment_id();
        this.author_id = rmb.getAuthor_id().getUser_id();
        if (rmb.getResolver_id() != null)
             this.resolver_id = rmb.getResolver_id().getUser_id();
        this.status_id = rmb.getStatus_id().getStatus_id();
        this.type_id = rmb.getType_id().getType_id();
    }

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
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

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getResolver_id() {
        return resolver_id;
    }

    public void setResolver_id(String resolver_id) {
        this.resolver_id = resolver_id;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
}
