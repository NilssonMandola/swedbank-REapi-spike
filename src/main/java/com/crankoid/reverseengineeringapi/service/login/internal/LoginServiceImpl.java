package com.crankoid.reverseengineeringapi.service.login.internal;

import com.crankoid.reverseengineeringapi.service.login.api.LoginService;
import com.crankoid.reverseengineeringapi.service.login.api.model.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private ExternalLoginServiceFactory factory;

    public LoginServiceImpl(ExternalLoginServiceFactory factory) {
        this.factory = factory;
    }

    @Override
    public LoginResponse login(String bic, String ssn) {
        return factory.getExternalLoginService(bic + "/LOGIN").login(ssn);
    }

    @Override
    public LoginResponse poll(String bic) {
        return factory.getExternalLoginService(bic + "/LOGIN").poll();
    }
}