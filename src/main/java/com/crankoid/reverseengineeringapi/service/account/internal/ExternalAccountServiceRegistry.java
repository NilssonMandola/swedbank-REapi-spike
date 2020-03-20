package com.crankoid.reverseengineeringapi.service.account.internal;

public interface ExternalAccountServiceRegistry {
    public ExternalAccountService getService(String bic);
}
