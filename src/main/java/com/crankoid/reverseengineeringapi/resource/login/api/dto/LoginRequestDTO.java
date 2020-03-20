package com.crankoid.reverseengineeringapi.resource.login.api.dto;

public class LoginRequestDTO {
    private String bic;
    private String ssn;

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
