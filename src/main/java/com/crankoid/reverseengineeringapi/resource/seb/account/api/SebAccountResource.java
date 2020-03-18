package com.crankoid.reverseengineeringapi.resource.seb.account.api;

import com.crankoid.reverseengineeringapi.service.seb.model.AccountsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequestMapping("seb")
public interface SebAccountResource {
    @GetMapping("accounts")
    public AccountsResponse getAccounts() throws IOException;
}
