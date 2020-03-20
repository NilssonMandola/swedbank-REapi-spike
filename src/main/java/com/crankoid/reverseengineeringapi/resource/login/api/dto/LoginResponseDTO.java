package com.crankoid.reverseengineeringapi.resource.login.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class LoginResponseDTO {
    public enum Status {
        OK, PENDING, ERROR
    }

    private Status status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String autoStartToken;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAutoStartToken() {
        return autoStartToken;
    }

    public void setAutoStartToken(String autoStartToken) {
        this.autoStartToken = autoStartToken;
    }
}
