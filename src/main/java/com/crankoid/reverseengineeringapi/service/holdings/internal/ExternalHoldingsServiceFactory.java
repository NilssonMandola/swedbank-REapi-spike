package com.crankoid.reverseengineeringapi.service.holdings.internal;

public interface ExternalHoldingsServiceFactory {
    public ExternalHoldingsService getExternalHoldingsService(String bic);
}
