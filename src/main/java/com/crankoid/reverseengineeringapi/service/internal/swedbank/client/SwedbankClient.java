package com.crankoid.reverseengineeringapi.service.internal.swedbank.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.CookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Component
public class SwedbankClient {
    private static final Logger logger = LogManager.getLogger(SwedbankClient.class);

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private HttpSession httpSession;
    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;

    public SwedbankClient(HttpSession httpSession, ObjectMapper objectMapper, CookieJar cookieJar) {
        this.httpSession = httpSession;
        this.objectMapper = objectMapper;
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .cookieJar(cookieJar).build();
    }

    public void sendLoginRequest(String userId) {
    }

    public void verifyLoginRequest() {
    }
}
