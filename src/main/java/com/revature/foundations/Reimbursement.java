package com.revature.foundations;

public class Reimbursement {
    private String id;
    private int amount;
    private String submitted;
    private String resolved;
    private String description;
    private String receipt;
    private String payment_id;
    private String author_id;
    private String resolve_id;
    private String status_id;
    private String type_id;

    public Reimbursement(){
        super();
    }

    public Reimbursement(int amount, String description){
        this.amount = amount;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
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

    public String getPayment_id() {
        return payment_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public String getResolve_id() {
        return resolve_id;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getType_id() {
        return type_id;
    }
}
