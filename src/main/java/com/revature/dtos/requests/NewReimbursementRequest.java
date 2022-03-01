package com.revature.dtos.requests;

import com.revature.models.Reimbursement;

public class NewReimbursementRequest {
    private float amount;
    private String description;
    private String receipt;
    private String payment_id;

    public NewReimbursementRequest(){super();}

    public NewReimbursementRequest(float amount, String description, String receipt, String payment_id) {
        this.amount = amount;
        this.description = description;
        this.receipt = receipt;
        this.payment_id = payment_id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
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

    public Reimbursement extractReimbursement(){
        return new Reimbursement(amount, description, receipt, payment_id);
    }

    @Override
    public String toString() {
        return "NewReimbursementRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", receipt='" + receipt + '\'' +
                ", payment_id='" + payment_id + '\'' +
                '}';
    }


}
