package com.revature.dtos.requests;

public class ReimbRequest {
    private String reimb_id;
    private String author_id;
    private String status_id;
    private String type_id;
    private String resolver_id;


    public ReimbRequest() {
        super();
    }

    public ReimbRequest(String reimb_id, String author_id, String status_id, String type_id, String resolver_id) {
        this.reimb_id = reimb_id;
        this.author_id = author_id;
        this.status_id = status_id;
        this.type_id = type_id;
        this.resolver_id = resolver_id;
    }

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
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

    public String getResolver_id() {
        return resolver_id;
    }

    public void setResolver_id(String resolver_id) {
        this.resolver_id = resolver_id;
    }

    @Override
    public String toString() {
        return "ReimbRequest{" +
                "reimb_id='" + reimb_id + '\'' +
                ", author_id='" + author_id + '\'' +
                ", status_id='" + status_id + '\'' +
                ", type_id='" + type_id + '\'' +
                ", resolver_id='" + resolver_id + '\'' +
                '}';
    }
}