package com.crankoid.reverseengineeringapi.resource.session.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("session")
public interface SessionResource {
    @DeleteMapping
    public void destroy();
}
