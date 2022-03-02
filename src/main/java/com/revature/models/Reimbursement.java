package com.revature.models;

import java.util.Objects;

public class Reimbursement {
    private String reimb_id;
    private Float amount;
    private String submitted;
    private String resolved;
    private String description;
    private String receipt;
    private String payment_id;
    private User author_id;
    private User resolver_id;
    private ReimbursementStatus status_id;
    private ReimbursementType type_id;

    public Reimbursement() {
        super();
    }

    public Reimbursement(Float amount, String submitted, String description, String receipt) {
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.receipt = receipt;
    }


    public Reimbursement(String reimb_id, Float amount, String submitted, String description, String receipt,
                         User resolver_id, ReimbursementStatus status_id, ReimbursementType type_id) {
        this.reimb_id = reimb_id;
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.receipt = receipt;
        this.resolver_id = resolver_id;
        this.status_id = status_id;
        this.type_id = type_id;
    }

    public Reimbursement( Float amount, String submitted, String description, String receipt,
                          User author_id, ReimbursementType type_id ){
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.receipt = receipt;
        this.author_id = author_id;
        this.type_id = type_id;

    }


    public String getReimb_id() {
        return reimb_id;
    }

    public Float getAmount() {
        return amount;
    }

    public String getSubmitted() {
        return submitted;
    }

    public String getResolved() {
        return resolved;
    }

    public String getDescription() {
        return description;
    }

    public String getReceipt() {
        return receipt;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public User getAuthor_id() {
        return author_id;
    }

    public User getResolver_id() {
        return resolver_id;
    }

    public ReimbursementStatus getStatus_id() {
        return status_id;
    }

    public ReimbursementType getType_id() {
        return type_id;
    }


    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public void setAuthor_id(User author_id) {
        this.author_id = author_id;
    }

    public void setResolver_id(User resolver_id) {
        this.resolver_id = resolver_id;
    }

    public void setStatus_id(ReimbursementStatus status_id) {
        this.status_id = status_id;
    }

    public void setType_id(ReimbursementType type_id) {
        this.type_id = type_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement reimbursement = (Reimbursement) o;
        return Objects.equals(reimb_id, reimbursement.reimb_id) && Objects.equals(amount, reimbursement.amount) &&
                Objects.equals(submitted, reimbursement.submitted) &&
                Objects.equals(resolved, reimbursement.resolved)&&
                Objects.equals(description, reimbursement.description) &&
                Objects.equals(receipt, reimbursement.receipt)&&
                Objects.equals(payment_id, reimbursement.payment_id) &&
                Objects.equals(author_id, reimbursement.author_id)&&
                Objects.equals(resolver_id, reimbursement.resolver_id) &&
                Objects.equals(status_id, reimbursement.status_id)&&
                Objects.equals(type_id, reimbursement.type_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimb_id, submitted, resolved, description, receipt, payment_id, author_id, resolver_id,
                status_id, type_id);
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id='" + reimb_id + '\'' +
                ", amount=" + amount +
                ", submitted='" + submitted + '\'' +
                ", resolved='" + resolved + '\'' +
                ", description='" + description + '\'' +
                ", receipt='" + receipt + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", author_id='" + author_id + '\'' +
                ", resolver_id='" + resolver_id + '\'' +
                ", status_id='" + status_id + '\'' +
                ", type_id='" + type_id + '\'' +
                '}';
    }
}