package com.crankoid.reverseengineeringapi.service.login.internal;

public interface ExternalLoginServiceRegistry {
    public ExternalLoginService getService(String bic);
}
