package com.crankoid.reverseengineeringapi.service.internal.swedbank.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    public boolean sendLoginRequest(String ssn) throws IOException {
        logger.debug("sendLoginRequest");

        String dsid = "BB7293C50244F95F:O8PQBMxkxLuRh5JD4YzjAyYuY6E=";
        String json = String.format("{\"generateEasyLoginId\":\"false\",\"useEasyLogin\":\"false\",\"userId\":\"%s\"}", ssn);

        RequestBody body = RequestBody.create(json, JSON);

        Request.Builder builder = new Request.Builder()
                .url(String.format("https://online.swedbank.se/TDE_DAP_Portal_REST_WEB/api/v5/identification/bankid/mobile?dsid=%s", dsid))
                .post(body);

        setCommonHeaders(builder);
        Request request = builder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            logger.debug(responseBody);

            return response.code() == 200;
        }

    }

    public boolean verifyLoginRequest() throws IOException {
        logger.debug("verifyLoginRequest");

        String dsid = "BB7293C50244F95F:O8PQBMxkxLuRh5JD4YzjAyYuY6E=";

        Request.Builder builder = new Request.Builder()
                .url(String.format("https://online.swedbank.se/TDE_DAP_Portal_REST_WEB/api/v5/identification/bankid/mobile/verify?dsid=%s", dsid));

        setCommonHeaders(builder);
        Request request = builder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            logger.debug(responseBody);

            getAuthenticatedProfile();
            postAuthenticatedProfile();

            return response.code() == 200;
        }
    }

    private boolean getAuthenticatedProfile() throws IOException {
        logger.debug("getAuthenticatedProfile");

        String dsid = "BB7293C50244F95F:O8PQBMxkxLuRh5JD4YzjAyYuY6E=";

        Request.Builder builder = new Request.Builder()
                .url(String.format("https://online.swedbank.se/TDE_DAP_Portal_REST_WEB/api/v5/profile/?dsid=%s", dsid));

        setCommonHeaders(builder);
        Request request = builder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            logger.debug(responseBody);

            JsonNode obj = objectMapper.readTree(responseBody);
            String id = obj.get("banks").get(0).get("privateProfile").get("id").textValue();

            httpSession.setAttribute("id", id);

            return response.code() == 200;
        }
    }

    private boolean postAuthenticatedProfile() throws IOException {
        logger.debug("postAuthenticatedProfile");

        String dsid = "BB7293C50244F95F:O8PQBMxkxLuRh5JD4YzjAyYuY6E=";
        String id = (String)httpSession.getAttribute("id");

        RequestBody body = RequestBody.create("", null);

        Request.Builder builder = new Request.Builder()
                .url(String.format("https://online.swedbank.se/TDE_DAP_Portal_REST_WEB/api/v5/profile/%s?dsid=%s", id, dsid))
                .post(body);

        setCommonHeaders(builder);
        Request request = builder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            logger.debug(responseBody);

            return response.code() == 200;
        }
    }

    private void setCommonHeaders(Request.Builder builder) {
        String authorization = "QjdkWkhRY1k3OFZSVno5bDoxNTc5NzA4MTcyMDg4";

        builder.addHeader("Authorization", String.format("%s", authorization))
            .addHeader("Accept", "application/json, text/plain, */*")
            .addHeader("X-Client", "fdp-nib/164.1.0")
            .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36")
            .addHeader("Content-Type", "application/json")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Host", "online.swedbank.se")
            .addHeader("Connection", "keep-alive");
    }
}
