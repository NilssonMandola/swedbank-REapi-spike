package com.crankoid.reverseengineeringapi.service.internal.seb.internal;

import com.crankoid.reverseengineeringapi.service.api.model.LoginResponse;
import com.crankoid.reverseengineeringapi.service.internal.seb.api.SebLoginService;
import com.crankoid.reverseengineeringapi.service.internal.seb.internal.client.SebClient;
import com.crankoid.reverseengineeringapi.service.internal.seb.internal.client.model.InitResponse;
import com.crankoid.reverseengineeringapi.service.internal.seb.internal.client.model.VerifyResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SebLoginServiceImpl implements SebLoginService {
    private SebClient sebClient;

    public SebLoginServiceImpl(SebClient sebClient) {
        this.sebClient = sebClient;
    }

    @Override
    public LoginResponse login(String bic, String ssn) {
        LoginResponse loginResponse = new LoginResponse();

        try {
            InitResponse initResponse = sebClient.postAuthentications();
            loginResponse.setStatus(LoginResponse.Status.PENDING);
            loginResponse.setAutostartToken(initResponse.getAuto_start_token());
        } catch (IOException e) {
            loginResponse.setStatus(LoginResponse.Status.ERROR);
        }

        return loginResponse;
    }

    @Override
    public LoginResponse poll(String bic) {
        LoginResponse loginResponse = new LoginResponse();

        try {
            VerifyResponse verifyResponse = sebClient.pollAuthenticate();
            loginResponse.setStatus(LoginResponse.Status.OK);
        } catch (IOException e) {
            loginResponse.setStatus(LoginResponse.Status.ERROR);
        }

        return loginResponse;
    }
}
