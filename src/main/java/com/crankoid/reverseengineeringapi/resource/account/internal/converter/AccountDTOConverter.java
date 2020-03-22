package com.crankoid.reverseengineeringapi.resource.account.internal.converter;

import com.crankoid.reverseengineeringapi.resource.account.api.dto.AccountDTO;
import com.crankoid.reverseengineeringapi.service.account.api.model.Account;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountDTOConverter implements Converter<Account, AccountDTO> {
    @Override
    public AccountDTO convert(Account source) {
        AccountDTO dto = new AccountDTO();

        dto.setAccountNumber(source.getAccountNumber());
        dto.setAccountName(source.getAccountName());
        dto.setAccountBalance(source.getAccountBalance());

        return dto;
    }

    public List<AccountDTO> convert(List<Account> source) {
        return source.stream().map(a -> convert(a)).collect(Collectors.toList());
    }
}
