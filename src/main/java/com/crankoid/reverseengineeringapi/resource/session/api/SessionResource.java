package com.crankoid.reverseengineeringapi.resource.session.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequestMapping("session")
public interface SessionResource {
    @GetMapping
    public String init() throws IOException;

    @DeleteMapping
    public void destroy();
}
