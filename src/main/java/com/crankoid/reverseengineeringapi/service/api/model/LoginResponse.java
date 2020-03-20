package com.crankoid.reverseengineeringapi.service.api.model;

public class LoginResponse {
    public enum Status {
        OK, PENDING, ERROR
    }

    private Status status;
    private String autostartToken;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAutostartToken() {
        return autostartToken;
    }

    public void setAutostartToken(String autostartToken) {
        this.autostartToken = autostartToken;
    }
}
