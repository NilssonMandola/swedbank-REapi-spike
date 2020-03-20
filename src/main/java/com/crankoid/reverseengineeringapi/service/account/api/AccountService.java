package com.crankoid.reverseengineeringapi.service.account.api;

import com.crankoid.reverseengineeringapi.service.account.api.model.AccountsResponse;

public interface AccountService {
    AccountsResponse getAccounts(String bic);
}
