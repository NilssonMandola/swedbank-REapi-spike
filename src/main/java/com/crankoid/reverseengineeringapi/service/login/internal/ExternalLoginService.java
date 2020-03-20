package com.crankoid.reverseengineeringapi.service.login.internal;

import com.crankoid.reverseengineeringapi.service.login.api.model.LoginResponse;

public interface ExternalLoginService {
    public LoginResponse login(String ssn);
    public LoginResponse poll();
}
