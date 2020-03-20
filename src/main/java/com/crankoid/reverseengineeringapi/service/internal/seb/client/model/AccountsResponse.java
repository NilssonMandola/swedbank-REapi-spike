package com.crankoid.reverseengineeringapi.service.internal.seb.client.model;

import java.util.List;

public class AccountsResponse {
    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}