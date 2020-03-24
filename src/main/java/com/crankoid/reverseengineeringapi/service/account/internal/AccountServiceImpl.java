package com.crankoid.reverseengineeringapi.service.account.internal;

import com.crankoid.reverseengineeringapi.service.account.api.AccountService;
import com.crankoid.reverseengineeringapi.service.account.api.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private ExternalAccountServiceFactory factory;

    public AccountServiceImpl(ExternalAccountServiceFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Account> getAccounts(String bic) {
        return factory.getExternalAccountService(bic + "/ACCOUNT").getAccounts();
    }
}
