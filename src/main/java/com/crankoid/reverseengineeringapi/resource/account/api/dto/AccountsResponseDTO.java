package com.crankoid.reverseengineeringapi.resource.account.api.dto;

import java.util.List;

public class AccountsResponseDTO {
    private List<AccountDTO> accounts;

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts) {
        this.accounts = accounts;
    }
}
