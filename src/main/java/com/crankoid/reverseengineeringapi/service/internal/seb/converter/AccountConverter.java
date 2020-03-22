package com.crankoid.reverseengineeringapi.service.internal.seb.converter;

import com.crankoid.reverseengineeringapi.service.account.api.model.Account;
import com.crankoid.reverseengineeringapi.service.internal.seb.client.model.SebAccount;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountConverter implements Converter<SebAccount, Account> {
    @Override
    public Account convert(SebAccount source) {
        Account account = new Account();

        account.setAccountNumber(source.getKontoNr());
        account.setAccountName(source.getKtoslagTxt());
        account.setAccountBalance(source.getBokfSaldo());

        return account;
    }

    public List<Account> convert(List<SebAccount> source) {
       return source.stream().map(a -> convert(a)).collect(Collectors.toList());
    }
}
