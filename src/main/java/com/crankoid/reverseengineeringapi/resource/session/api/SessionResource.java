package com.crankoid.reverseengineeringapi.resource.session.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("session")
@Tag(name = "Session")
public interface SessionResource {
    @DeleteMapping
    public void destroy();
}
