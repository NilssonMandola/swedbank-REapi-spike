package com.crankoid.reverseengineeringapi.service.account.internal;

import com.crankoid.reverseengineeringapi.service.account.api.model.Account;

import java.util.List;

public interface ExternalAccountService {
    public List<Account> getAccounts();
}
