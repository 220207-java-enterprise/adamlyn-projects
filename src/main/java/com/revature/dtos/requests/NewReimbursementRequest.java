package com.revature.dtos.requests;

import com.revature.models.Reimbursement;
import com.revature.models.User;

public class NewReimbursementRequest {

    private Float amount;
    private String submitted;
    private String description;
    private String receipt;
    private String author_id;
    private String type_id;


    public NewReimbursementRequest(){super();}

    public NewReimbursementRequest(Float amount, String submitted, String description, String receipt,
                                   String author_id, String type_id) {
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.receipt = receipt;
        this.author_id = author_id;
        this.type_id = type_id;
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

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public Reimbursement extractReimb(){
        return new Reimbursement(amount, submitted, description, receipt);
    }

    @Override
    public String toString() {
        return "NewReimbursementRequest{" +
                "amount=" + amount +
                ", submitted='" + submitted + '\'' +
                ", description='" + description + '\'' +
                ", receipt='" + receipt + '\'' +
                ", author_id=" + author_id +
                ", type_id='" + type_id + '\'' +
                '}';
    }
}
