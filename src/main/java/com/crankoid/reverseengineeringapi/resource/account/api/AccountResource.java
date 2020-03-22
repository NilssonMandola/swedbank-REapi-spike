package com.crankoid.reverseengineeringapi.resource.account.api;

import com.crankoid.reverseengineeringapi.resource.account.api.dto.AccountsResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("accounts")
public interface AccountResource {
    @GetMapping("/{bic}")
    public AccountsResponseDTO getAccounts(@PathVariable(required = true) String bic);
}
