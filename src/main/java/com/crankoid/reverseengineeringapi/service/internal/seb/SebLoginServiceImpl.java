package com.crankoid.reverseengineeringapi.service.internal.seb;

import com.crankoid.reverseengineeringapi.service.login.api.model.LoginResponse;
import com.crankoid.reverseengineeringapi.service.login.internal.ExternalLoginService;
import com.crankoid.reverseengineeringapi.service.internal.seb.client.SebClient;
import com.crankoid.reverseengineeringapi.service.internal.seb.client.model.InitResponse;
import com.crankoid.reverseengineeringapi.service.internal.seb.client.model.VerifyResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("ESSESESS")
public class SebLoginServiceImpl implements ExternalLoginService {
    private SebClient sebClient;

    public SebLoginServiceImpl(SebClient sebClient) {
        this.sebClient = sebClient;
    }

    @Override
    public LoginResponse login(String ssn) {
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
    public LoginResponse poll() {
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
