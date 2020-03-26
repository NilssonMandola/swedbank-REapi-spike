package com.crankoid.reverseengineeringapi.service.internal.seb.client;

import com.crankoid.reverseengineeringapi.service.internal.seb.client.model.SebAccount;
import com.crankoid.reverseengineeringapi.service.internal.seb.client.model.InitResponse;
import com.crankoid.reverseengineeringapi.service.internal.seb.client.model.VerifyResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class SebClient {
    private static final Logger logger = LogManager.getLogger(SebClient.class);

    private static final String SEB = "seb";

    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private HttpSession httpSession;
    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;

    public SebClient(HttpSession httpSession, ObjectMapper objectMapper, CookieJar cookieJar) {
        this.httpSession = httpSession;
        this.objectMapper = objectMapper;
        this.okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .cookieJar(cookieJar).build();
    }

    @CircuitBreaker(name = SEB)
    public InitResponse postAuthentications() throws IOException {
        logger.debug("postAuthentications");
        RequestBody body = RequestBody.create("", null);

        Request.Builder builder = new Request.Builder()
                .url("https://mp.seb.se/auth/bid/v2/authentications")
                .post(body);

        setCommonHeaders(builder);
        Request request = builder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            logger.debug(responseBody);

            String header = response.header("x-seb-csrf");
            httpSession.setAttribute("x-seb-csrf", header);

            InitResponse initResponse =
                    objectMapper.readValue(responseBody, InitResponse.class);

            return initResponse;
        }
    }

    @CircuitBreaker(name = SEB)
    public VerifyResponse pollAuthenticate() throws IOException {
        logger.debug("pollAuthenticate");

        Request.Builder builder = new Request.Builder()
                .url("https://mp.seb.se/auth/bid/v2/authentications")
                .addHeader("x-seb-csrf", (String)httpSession.getAttribute("x-seb-csrf"));

        setCommonHeaders(builder);
        Request request = builder.build();


        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            logger.debug(responseBody);

            initSession();
            return activateSession();
        }
    }

    @CircuitBreaker(name = SEB)
    private boolean initSession() throws IOException {
        logger.debug("initSession");
        String json = "{\n" +
                "\t\"request\": {\n" +
                "\t\t\"UserCredentials\": {\n" +
                "\t\t\t\"ApplicationName\": \"MASP\",\n" +
                "\t\t\t\"UserId\": \"\"\n" +
                "\t\t},\n" +
                "\t\t\"__type\": \"SEB_CS.SEBCSService\",\n" +
                "\t\t\"ServiceInput\": []\n" +
                "\t}\n" +
                "}";

        RequestBody body = RequestBody.create(json, JSON);

        Request.Builder builder = new Request.Builder()
                .url("https://mp.seb.se/1000/ServiceFactory/PC_BANK/PC_BankInit11Session01.asmx/Execute")
                .addHeader("x-iam-version", "3")
                .post(body);

        setCommonHeaders(builder);
        Request request = builder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            logger.debug(responseBody);

            return response.code() == 200;
        }
    }

    @CircuitBreaker(name = SEB)
    private VerifyResponse activateSession() throws IOException {
        logger.debug("activateSession");
        String json ="{\n" +
                "\t\"request\": {\n" +
                "\t\t\"VODB\": {\n" +
                "\t\t\t\"DEVID01\": {\n" +
                "\t\t\t\t\"MANUFACTURER\": \"Apple\",\n" +
                "\t\t\t\t\"OS_VERSION\": \"13.3\",\n" +
                "\t\t\t\t\"APPLICATION_NAME\": \"MASP\",\n" +
                "\t\t\t\t\"OS_NAME\": \"iOS\",\n" +
                "\t\t\t\t\"APPLICATION_VERSION\": \"10.4.0\",\n" +
                "\t\t\t\t\"MODEL\": \"API_VERSION=2\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"HWINFO01\": {}\n" +
                "\t\t},\n" +
                "\t\t\"__type\": \"SEB_CS.SEBCSService\",\n" +
                "\t\t\"ServiceInput\": [{\n" +
                "\t\t\t\"VariableName\": \"CUSTOMERTYPE\",\n" +
                "\t\t\t\"Condition\": \"EQ\",\n" +
                "\t\t\t\"VariableValue\": \"P\"\n" +
                "\t\t}]\n" +
                "\t}\n" +
                "}";

        RequestBody body = RequestBody.create(json, JSON);

        Request.Builder builder = new Request.Builder()
                .url("https://mp.seb.se/1000/ServiceFactory/PC_BANK/PC_BankAktivera01Session01.asmx/Execute")
                .addHeader("x-iam-version", "3")
                .post(body);

        setCommonHeaders(builder);
        Request request = builder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            logger.debug(responseBody);

            JsonNode obj = objectMapper.readTree(responseBody);
            JsonNode userinf01 = obj.get("d").get("VODB").get("USRINF01");

            VerifyResponse verifyResponse = objectMapper.convertValue(userinf01, VerifyResponse.class);
            httpSession.setAttribute("SEB_KUND_NR", verifyResponse.getSebKundNr());
            return verifyResponse;
        }
    }

    @CircuitBreaker(name = SEB)
    public List<SebAccount> getAccounts() throws IOException {
        logger.debug("getAccounts");

        String json = String.format("{\n" +
                "\t\"request\": {\n" +
                "\t\t\"__type\": \"SEB_CS.SEBCSService\",\n" +
                "\t\t\"ServiceInput\": [{\n" +
                "\t\t\t\"VariableName\": \"KUND_ID\",\n" +
                "\t\t\t\"Condition\": \"EQ\",\n" +
                "\t\t\t\"VariableValue\": \"%s\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"VariableName\": \"KONTO_TYP\",\n" +
                "\t\t\t\"Condition\": \"EQ\",\n" +
                "\t\t\t\"VariableValue\": \"ICKEFOND\"\n" +
                "\t\t}]\n" +
                "\t}\n" +
                "}", httpSession.getAttribute("\"SEB_KUND_NR\""));

        RequestBody body = RequestBody.create(json, JSON);

        Request.Builder builder = new Request.Builder()
                .url("https://mp.seb.se/1000/ServiceFactory/PC_BANK/PC_BankLista01Konton_privat01.asmx/Execute")
                .addHeader("x-iam-version", "3")
                .post(body);

        setCommonHeaders(builder);
        Request request = builder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            logger.debug(responseBody);

            JsonNode obj = objectMapper.readTree(responseBody);
            JsonNode pcbw4211 = obj.get("d").get("VODB").get("PCBW4211");

            return Arrays.asList(objectMapper.convertValue(pcbw4211, SebAccount[].class));
        }
    }

    private void setCommonHeaders(Request.Builder builder) {
        builder.addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("SEBLanguage", "sv-SE")
            .addHeader("x-seb-uuid", UUID.randomUUID().toString())
            .addHeader("Connection", "keep-alive")
            .addHeader("Accept", "application/json")
            .addHeader("User-Agent", "SEBapp/1.0 (os=iOS/13.3; app=se.seb.privatkund/10.4)")
            .addHeader("Accept-Language", "sv-SE");
    }
}
