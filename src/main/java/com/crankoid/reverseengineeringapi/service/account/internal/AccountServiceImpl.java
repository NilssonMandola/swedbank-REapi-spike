package com.crankoid.reverseengineeringapi.service.account.internal;

import com.crankoid.reverseengineeringapi.service.account.api.AccountService;
import com.crankoid.reverseengineeringapi.service.account.api.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private ExternalAccountServiceRegistry registry;

    public AccountServiceImpl(ExternalAccountServiceRegistry registry) {
        this.registry = registry;
    }

    @Override
    public List<Account> getAccounts(String bic) {
        return registry.getService(bic + "/ACCOUNT").getAccounts();
    }
}
