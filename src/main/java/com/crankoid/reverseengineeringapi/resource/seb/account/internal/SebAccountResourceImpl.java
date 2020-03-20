package com.crankoid.reverseengineeringapi.resource.seb.account.internal;

import com.crankoid.reverseengineeringapi.resource.seb.account.api.SebAccountResource;
import com.crankoid.reverseengineeringapi.service.internal.seb.internal.client.SebClient;
import com.crankoid.reverseengineeringapi.service.internal.seb.internal.client.model.AccountsResponse;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SebAccountResourceImpl implements SebAccountResource {
    private SebClient sebClient;

    public SebAccountResourceImpl(SebClient sebClient) {
        this.sebClient = sebClient;
    }

    public AccountsResponse getAccounts() throws IOException {
        return sebClient.getAccounts();
    }
}
