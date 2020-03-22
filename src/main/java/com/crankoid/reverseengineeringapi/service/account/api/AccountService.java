package com.crankoid.reverseengineeringapi.service.account.api;

import com.crankoid.reverseengineeringapi.service.account.api.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAccounts(String bic);
}
