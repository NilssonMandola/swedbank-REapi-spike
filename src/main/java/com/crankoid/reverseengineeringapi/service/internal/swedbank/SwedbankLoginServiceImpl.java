package com.crankoid.reverseengineeringapi.service.internal.swedbank;

import com.crankoid.reverseengineeringapi.service.internal.swedbank.client.SwedbankClient;
import com.crankoid.reverseengineeringapi.service.login.api.model.LoginResponse;
import com.crankoid.reverseengineeringapi.service.login.internal.ExternalLoginService;
import org.springframework.stereotype.Service;

@Service("SWEDSESS/LOGIN")
public class SwedbankLoginServiceImpl  implements ExternalLoginService {
    private SwedbankClient swedbankClient;

    public SwedbankLoginServiceImpl(SwedbankClient swedbankClient) {
        this.swedbankClient = swedbankClient;
    }

    @Override
    public LoginResponse login(String ssn) {
        LoginResponse loginResponse = new LoginResponse();
        
        swedbankClient.sendLoginRequest(ssn);

        loginResponse.setStatus(LoginResponse.Status.PENDING);

        return loginResponse;
    }

    @Override
    public LoginResponse poll() {
        LoginResponse loginResponse = new LoginResponse();

        swedbankClient.verifyLoginRequest();

        loginResponse.setStatus(LoginResponse.Status.OK);

        return loginResponse;
    }
}
