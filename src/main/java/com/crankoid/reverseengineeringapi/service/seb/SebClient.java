package com.crankoid.reverseengineeringapi.service.seb;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Component
public class SebClient {
    private static final String COOKIE_NAME_PREFIX = "cookie_";

    private HttpSession httpSession;
    private OkHttpClient okHttpClient;

    public SebClient(HttpSession httpSession) {
        this.httpSession = httpSession;
        this.okHttpClient = new OkHttpClient.Builder().cookieJar(cookieJar).build();
    }

    public String startAuthenticate() throws IOException {
        RequestBody body = RequestBody.create("", null);

        Request request = new Request.Builder()
                .url("https://mp.seb.se/auth/bid/v2/authentications")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("SEBLanguage", "sv-SE")
                .addHeader("x-seb-uuid", UUID.randomUUID().toString())
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "SEBapp/1.0 (os=iOS/13.3; app=se.seb.privatkund/10.4)")
                .addHeader("Accept-Language", "sv-SE")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Content-Length", "0")
                .post(body)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String header = response.header("x-seb-csrf");
            httpSession.setAttribute("x-seb-csrf", header);
            return response.body().string();
        }
    }

    public String pollAuthenticate() throws IOException {
        Request request = new Request.Builder()
                .url("https://mp.seb.se/auth/bid/v2/authentications")
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("SEBLanguage", "sv-SE")
                .addHeader("x-seb-uuid", UUID.randomUUID().toString())
                .addHeader("x-seb-csrf", (String)httpSession.getAttribute("x-seb-csrf"))
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "SEBapp/1.0 (os=iOS/13.3; app=se.seb.privatkund/10.4)")
                .addHeader("Accept-Language", "sv-SE")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }


    CookieJar cookieJar = new CookieJar() {
        @Override
        public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
            for (Cookie cookie : list) {
                httpSession.setAttribute(COOKIE_NAME_PREFIX + cookie.name(), new SerializableCookie(cookie));
            }
        }

        @NotNull
        @Override
        public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
            Enumeration<String> names = httpSession.getAttributeNames();

            List<Cookie> cookies = new ArrayList<>();

            while (names.hasMoreElements()) {
               String name = names.nextElement();
               if (name.startsWith(COOKIE_NAME_PREFIX)) {
                   SerializableCookie serializableCookie = (SerializableCookie)httpSession.getAttribute(name);
                   Cookie cookie = serializableCookie.getCookie();
                   cookies.add(cookie);
               }
            }

            return cookies;
        }
    };

}
