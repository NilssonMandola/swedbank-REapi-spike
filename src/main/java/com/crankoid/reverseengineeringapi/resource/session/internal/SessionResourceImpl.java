package com.crankoid.reverseengineeringapi.resource.session.internal;

import com.crankoid.reverseengineeringapi.resource.session.api.SessionResource;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SessionResourceImpl implements SessionResource {
    private HttpSession httpSession;

    public SessionResourceImpl(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void destroy() {
        httpSession.invalidate();
    }

}
