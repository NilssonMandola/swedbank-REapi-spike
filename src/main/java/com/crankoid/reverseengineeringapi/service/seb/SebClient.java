package com.crankoid.reverseengineeringapi.service.seb;

import okhttp3.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Component
public class SebClient {
    private HttpSession httpSession;

    public SebClient(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    OkHttpClient client = new OkHttpClient();

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

        try (Response response = client.newCall(request).execute()) {
            String header = response.header("x-seb-csrf");
            httpSession.setAttribute("x-seb-csrf", header);
            return response.body().string();
        }
    }
}
