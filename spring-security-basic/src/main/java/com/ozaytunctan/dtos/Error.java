package com.ozaytunctan.dtos;

import java.io.Serializable;

public class Error implements Serializable {

    private int status;

    private String message;

    private String field;

    public Error(){}

    public Error(int status, String message, String field) {
        this.status = status;
        this.message = message;
        this.field = field;
    }

    public Error(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public Error(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public Error(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "Error{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", field='" + field + '\'' +
                '}';
    }
}
