package com.crankoid.reverseengineeringapi.service.internal.seb.api;

import com.crankoid.reverseengineeringapi.service.api.model.LoginResponse;

public interface SebLoginService {
    public LoginResponse login(String bic, String ssn);
    public LoginResponse poll(String bic);
}
