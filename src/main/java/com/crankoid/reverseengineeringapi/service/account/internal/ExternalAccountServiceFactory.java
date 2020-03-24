package com.crankoid.reverseengineeringapi.service.account.internal;

public interface ExternalAccountServiceFactory {
    public ExternalAccountService getExternalAccountService(String bic);
}
