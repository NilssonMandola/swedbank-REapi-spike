package com.crankoid.reverseengineeringapi.service.internal.seb;

import com.crankoid.reverseengineeringapi.service.account.api.model.Account;
import com.crankoid.reverseengineeringapi.service.account.internal.ExternalAccountService;
import com.crankoid.reverseengineeringapi.service.internal.seb.client.SebClient;
import com.crankoid.reverseengineeringapi.service.internal.seb.converter.AccountConverter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service("ESSESESS/ACCOUNT")
public class SebAccountServiceImpl implements ExternalAccountService {
    private SebClient sebClient;
    private AccountConverter converter;

    public SebAccountServiceImpl(SebClient sebClient, AccountConverter converter) {
        this.sebClient = sebClient;
        this.converter = converter;
    }

    @Override
    public List<Account> getAccounts() {
        try {
            return converter.convert(sebClient.getAccounts());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
