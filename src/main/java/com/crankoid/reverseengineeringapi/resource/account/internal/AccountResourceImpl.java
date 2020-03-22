package com.crankoid.reverseengineeringapi.resource.account.internal;

import com.crankoid.reverseengineeringapi.resource.account.api.AccountResource;
import com.crankoid.reverseengineeringapi.resource.account.api.dto.AccountDTO;
import com.crankoid.reverseengineeringapi.resource.account.api.dto.AccountsResponseDTO;
import com.crankoid.reverseengineeringapi.resource.account.internal.converter.AccountDTOConverter;
import com.crankoid.reverseengineeringapi.service.account.api.AccountService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountResourceImpl implements AccountResource {
    private AccountService accountService;
    private AccountDTOConverter converter;

    public AccountResourceImpl(AccountService accountService, AccountDTOConverter converter) {
        this.accountService = accountService;
        this.converter = converter;
    }

    @Override
    public AccountsResponseDTO getAccounts(String bic) {
        AccountsResponseDTO accountsResponse = new AccountsResponseDTO();
        List<AccountDTO> accounts = converter.convert(accountService.getAccounts(bic));
        accountsResponse.setAccounts(accounts);
        return accountsResponse;
    }
}
