package com.crankoid.reverseengineeringapi.resource.seb.login.internal;

import com.crankoid.reverseengineeringapi.resource.seb.login.api.SebLoginResource;
import com.crankoid.reverseengineeringapi.service.seb.SebClient;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class SebLoginResourceImpl implements SebLoginResource {
    private HttpSession httpSession;
    private SebClient sebClient;

    public SebLoginResourceImpl(HttpSession httpSession, SebClient sebClient) {
        this.httpSession = httpSession;
        this.sebClient = sebClient;
    }

    public String start() throws IOException {
        return sebClient.startAuthenticate();
    }

    public String poll() throws IOException {
        return sebClient.pollAuthenticate();
    }
}
