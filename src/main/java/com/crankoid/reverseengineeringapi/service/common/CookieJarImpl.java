package com.crankoid.reverseengineeringapi.service.common;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Component
public class CookieJarImpl implements CookieJar {
    private static final String COOKIE_NAME_PREFIX = "cookie_";

    private HttpSession httpSession;

    public CookieJarImpl(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        Enumeration<String> names = httpSession.getAttributeNames();

        List<Cookie> cookies = new ArrayList<>();

        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.startsWith(COOKIE_NAME_PREFIX)) {
                SerializableCookie serializableCookie = (SerializableCookie)httpSession.getAttribute(name);
                Cookie cookie = serializableCookie.getCookie();

                if (httpUrl.host().equals(cookie.domain())) {
                    cookies.add(cookie);
                }
            }
        }

        return cookies;
    }

    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        for (Cookie cookie : list) {
            httpSession.setAttribute(COOKIE_NAME_PREFIX + cookie.name(), new SerializableCookie(cookie));
        }
    }
}
