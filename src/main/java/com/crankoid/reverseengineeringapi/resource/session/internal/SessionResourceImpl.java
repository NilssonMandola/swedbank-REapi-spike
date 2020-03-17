package com.crankoid.reverseengineeringapi.resource.session.internal;

import com.crankoid.reverseengineeringapi.resource.session.api.SessionResource;
import com.crankoid.reverseengineeringapi.service.seb.SebClient;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class SessionResourceImpl implements SessionResource {
    private HttpSession httpSession;
    private SebClient sebClient;

    public SessionResourceImpl(HttpSession httpSession, SebClient sebClient) {
        this.httpSession = httpSession;
        this.sebClient = sebClient;
    }

    public String init() throws IOException {
        return sebClient.startAuthenticate();
    }

    public void destroy() {
        httpSession.invalidate();
    }

}
