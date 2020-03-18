package com.crankoid.reverseengineeringapi.service.seb;

import com.crankoid.reverseengineeringapi.service.seb.model.Account;
import com.crankoid.reverseengineeringapi.service.seb.model.AccountsResponse;
import com.crankoid.reverseengineeringapi.service.seb.model.InitResponse;
import com.crankoid.reverseengineeringapi.service.seb.model.VerifyResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Component
public class SebClient {
    private static final String COOKIE_NAME_PREFIX = "cookie_";
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private HttpSession httpSession;
    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;

    public SebClient(HttpSession httpSession, ObjectMapper objectMapper) {
        this.httpSession = httpSession;
        this.objectMapper = objectMapper;
        this.okHttpClient = new OkHttpClient.Builder().cookieJar(cookieJar).build();
    }

    public InitResponse postAuthentications() throws IOException {
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
                .post(body)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            String header = response.header("x-seb-csrf");
            httpSession.setAttribute("x-seb-csrf", header);

            InitResponse initResponse =
                    objectMapper.readValue(response.body().string(), InitResponse.class);

            return initResponse;
        }
    }

    public VerifyResponse pollAuthenticate() throws IOException {
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
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
               initSession();
               return activateSession();
        }
    }

    private boolean initSession() throws IOException {
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

        Request request = new Request.Builder()
                .url("https://mp.seb.se/1000/ServiceFactory/PC_BANK/PC_BankInit11Session01.asmx/Execute")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("SEBLanguage", "sv-SE")
                .addHeader("x-seb-uuid", UUID.randomUUID().toString())
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "SEBapp/1.0 (os=iOS/13.3; app=se.seb.privatkund/10.4)")
                .addHeader("Accept-Language", "sv-SE")
                .addHeader("x-iam-version", "3")
                .post(body)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.code() == 200;
        }
    }

    private VerifyResponse activateSession() throws IOException {
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

        Request request = new Request.Builder()
                .url("https://mp.seb.se/1000/ServiceFactory/PC_BANK/PC_BankAktivera01Session01.asmx/Execute")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("SEBLanguage", "sv-SE")
                .addHeader("x-seb-uuid", UUID.randomUUID().toString())
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "SEBapp/1.0 (os=iOS/13.3; app=se.seb.privatkund/10.4)")
                .addHeader("Accept-Language", "sv-SE")
                .addHeader("x-iam-version", "3")
                .post(body)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            JsonNode obj = objectMapper.readTree(response.body().string());
            JsonNode userinf01 = obj.get("d").get("VODB").get("USRINF01");

            VerifyResponse verifyResponse = objectMapper.convertValue(userinf01, VerifyResponse.class);
            httpSession.setAttribute("SEB_KUND_NR", verifyResponse.getSebKundNr());
            return verifyResponse;
        }
    }

    public AccountsResponse getAccounts() throws IOException {
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

        Request request = new Request.Builder()
                .url("https://mp.seb.se/1000/ServiceFactory/PC_BANK/PC_BankLista01Konton_privat01.asmx/Execute")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("SEBLanguage", "sv-SE")
                .addHeader("x-seb-uuid", UUID.randomUUID().toString())
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "SEBapp/1.0 (os=iOS/13.3; app=se.seb.privatkund/10.4)")
                .addHeader("Accept-Language", "sv-SE")
                .addHeader("x-iam-version", "3")
                .post(body)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            JsonNode obj = objectMapper.readTree(response.body().string());
            JsonNode pcbw4211 = obj.get("d").get("VODB").get("PCBW4211");

            List<Account> accounts = Arrays.asList(objectMapper.convertValue(pcbw4211, Account[].class));

            AccountsResponse accountsResponse = new AccountsResponse();
            accountsResponse.setAccounts(accounts);

            return accountsResponse;
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
