package com.revature.dtos.requests;

public class ResourceCreationResponse {
    private String id;

    public ResourceCreationResponse(){
        super();
    }
    public ResourceCreationResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
