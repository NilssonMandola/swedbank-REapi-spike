package com.crankoid.reverseengineeringapi.service.internal.swedbank.client.model;

public class SwedbankHolding {
    private String id;
    private String name;
    private String accountNumber;
    private String clearingNumber;
    private String fullyFormattedNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getClearingNumber() {
        return clearingNumber;
    }

    public void setClearingNumber(String clearingNumber) {
        this.clearingNumber = clearingNumber;
    }

    public String getFullyFormattedNumber() {
        return fullyFormattedNumber;
    }

    public void setFullyFormattedNumber(String fullyFormattedNumber) {
        this.fullyFormattedNumber = fullyFormattedNumber;
    }
}
