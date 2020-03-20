package com.crankoid.reverseengineeringapi.service.login.api;

import com.crankoid.reverseengineeringapi.service.login.api.model.LoginResponse;

public interface LoginService {
    public LoginResponse login(String bic, String ssn);

    public LoginResponse poll(String bic);
}
