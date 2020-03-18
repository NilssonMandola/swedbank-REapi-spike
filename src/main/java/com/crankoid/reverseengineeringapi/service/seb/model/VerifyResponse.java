package com.crankoid.reverseengineeringapi.service.seb.model;

import com.fasterxml.jackson.annotation.JsonGetter;

public class VerifyResponse {
    private String status;
    private String loginMethod;
    private String sebKundNr;
    private String userName;
    private String gender;
    private String age;

    @JsonGetter("STATUS")
    public String getStatus() {
        return status;
    }

    @JsonGetter("LOGIN_METHOD")
    public String getLoginMethod() {
        return loginMethod;
    }

    @JsonGetter("SEB_KUND_NR")
    public String getSebKundNr() {
        return sebKundNr;
    }

    @JsonGetter("USER_NAME")
    public String getUserName() {
        return userName;
    }

    @JsonGetter("GENDER")
    public String getGender() {
        return gender;
    }

    @JsonGetter("AGE")
    public String getAge() {
        return age;
    }
}