package com.crankoid.reverseengineeringapi.service.login.internal;

public interface ExternalLoginServiceFactory {
    public ExternalLoginService getExternalLoginService(String bic);
}
