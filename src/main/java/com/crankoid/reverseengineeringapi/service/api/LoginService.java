package com.crankoid.reverseengineeringapi.service.api;

import com.crankoid.reverseengineeringapi.service.api.model.LoginResponse;

public interface LoginService {
    public LoginResponse login(String bic, String ssn);

    public LoginResponse poll(String bic);
}
