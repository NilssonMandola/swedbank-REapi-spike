package com.crankoid.reverseengineeringapi.service.internal;

import com.crankoid.reverseengineeringapi.service.api.LoginService;
import com.crankoid.reverseengineeringapi.service.api.model.LoginResponse;
import com.crankoid.reverseengineeringapi.service.internal.seb.api.SebLoginService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private SebLoginService sebLoginService;

    public LoginServiceImpl(SebLoginService sebLoginService) {
        this.sebLoginService = sebLoginService;
    }

    @Override
    public LoginResponse login(String bic, String ssn) {
        // Replace with factory
        if ("SWEDSESS".equals(bic)) {
            return sebLoginService.login(bic, ssn);
        }

        return null;
    }

    @Override
    public LoginResponse poll(String bic) {
        // Replace with factory
        if ("SWEDSESS".equals(bic)) {
            return sebLoginService.poll(bic);
        }
        return null;
    }
}