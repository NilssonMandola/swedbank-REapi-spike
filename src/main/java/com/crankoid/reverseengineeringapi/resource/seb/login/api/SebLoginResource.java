package com.crankoid.reverseengineeringapi.resource.seb.login.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequestMapping("seb")
public interface SebLoginResource {
    @GetMapping("login/start")
    public String start() throws IOException;

    @GetMapping("login/poll")
    public String poll() throws IOException;
}
