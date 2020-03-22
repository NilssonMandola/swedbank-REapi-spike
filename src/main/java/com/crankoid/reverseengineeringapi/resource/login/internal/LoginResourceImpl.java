package com.crankoid.reverseengineeringapi.resource.login.internal;

import com.crankoid.reverseengineeringapi.resource.login.api.LoginResource;
import com.crankoid.reverseengineeringapi.resource.login.api.dto.LoginRequestDTO;
import com.crankoid.reverseengineeringapi.resource.login.api.dto.LoginResponseDTO;
import com.crankoid.reverseengineeringapi.resource.login.internal.converter.LoginResponseDTOConverter;
import com.crankoid.reverseengineeringapi.service.login.api.LoginService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginResourceImpl implements LoginResource {
    private LoginService loginService;
    private LoginResponseDTOConverter converter;

    public LoginResourceImpl(LoginService loginService, LoginResponseDTOConverter converter) {
        this.loginService = loginService;
        this.converter = converter;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        String bic = loginRequest.getBic();
        String ssn = loginRequest.getSsn();

        return converter.convert(loginService.login(bic, ssn));
    }

    @Override
    public LoginResponseDTO poll(String bic) {
        return converter.convert(loginService.poll(bic));
    }
}
