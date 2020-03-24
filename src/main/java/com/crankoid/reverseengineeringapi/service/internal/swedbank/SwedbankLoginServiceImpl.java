package com.crankoid.reverseengineeringapi.service.internal.swedbank;

import com.crankoid.reverseengineeringapi.service.internal.swedbank.client.SwedbankClient;
import com.crankoid.reverseengineeringapi.service.login.api.model.LoginResponse;
import com.crankoid.reverseengineeringapi.service.login.internal.ExternalLoginService;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.io.IOException;

@Service("SWEDSESS/LOGIN")
public class SwedbankLoginServiceImpl  implements ExternalLoginService {
    private SwedbankClient swedbankClient;

    public SwedbankLoginServiceImpl(SwedbankClient swedbankClient) {
        this.swedbankClient = swedbankClient;
    }

    @Override
    public LoginResponse login(String ssn) {
        LoginResponse loginResponse = new LoginResponse();

        try {
            swedbankClient.sendLoginRequest(ssn);
            loginResponse.setStatus(LoginResponse.Status.PENDING);
        } catch (IOException e) {
            loginResponse.setStatus(LoginResponse.Status.ERROR);
        }

        return loginResponse;
    }

    @Override
    public LoginResponse poll() {
        LoginResponse loginResponse = new LoginResponse();

        try {
            swedbankClient.verifyLoginRequest();
            loginResponse.setStatus(LoginResponse.Status.OK);
        } catch (IOException e) {
            loginResponse.setStatus(LoginResponse.Status.ERROR);
        }

        return loginResponse;
    }
}
