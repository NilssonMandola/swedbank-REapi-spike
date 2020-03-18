package com.crankoid.reverseengineeringapi.resource.seb.login.api;

import com.crankoid.reverseengineeringapi.service.seb.model.InitResponse;
import com.crankoid.reverseengineeringapi.service.seb.model.VerifyResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequestMapping("seb")
public interface SebLoginResource {
    @PostMapping("login/init")
    public InitResponse init() throws IOException;

    @GetMapping("login/verify")
    public VerifyResponse verify() throws IOException;
}
