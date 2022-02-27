package com.revature.models;

import java.util.Objects;

public class ReimbursementType {

    private String type_id;
    private String type;

    public ReimbursementType() {
        super();
    }

    public ReimbursementType(String typeid, String type) {
        this.type_id = typeid;
        this.type = type;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimbursementType that = (ReimbursementType) o;
        return Objects.equals(type_id, that.type_id) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type_id, type);
    }

    @Override
    public String toString() {
        return "ReimbursementType{" +
                "type_id='" + type_id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
