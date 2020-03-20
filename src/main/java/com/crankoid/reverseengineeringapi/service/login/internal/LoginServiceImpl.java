package com.crankoid.reverseengineeringapi.service.login.internal;

import com.crankoid.reverseengineeringapi.service.login.api.LoginService;
import com.crankoid.reverseengineeringapi.service.login.api.model.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private ExternalLoginServiceRegistry registry;

    public LoginServiceImpl(ExternalLoginServiceRegistry registry) {
        this.registry = registry;
    }

    @Override
    public LoginResponse login(String bic, String ssn) {
        return registry.getService(bic).login(ssn);
    }

    @Override
    public LoginResponse poll(String bic) {
        return registry.getService(bic).poll();
    }
}