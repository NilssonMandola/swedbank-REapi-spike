package com.crankoid.reverseengineeringapi.resource.swedbank;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RequestMapping("swedbank")
@RestController
public class SwedbankLoginResourceImpl {

    private String jsessionid;
    private String swbtc;
    private String id;

    @PostMapping("login/init")
    public void sendLoginRequest(String userId) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            String dsid = "BB7293C50244F95F:O8PQBMxkxLuRh5JD4YzjAyYuY6E=";

            HttpPost httpPost = new HttpPost(String.format("https://online.swedbank.se/TDE_DAP_Portal_REST_WEB/api/v5/identification/bankid/mobile?dsid=%s", dsid));

            setCommonHeaders(httpPost);

            String json = String.format("{\"generateEasyLoginId\":\"false\",\"useEasyLogin\":\"false\",\"userId\":\"%s\"}", userId);
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);

            CloseableHttpResponse response = client.execute(httpPost);
            saveSessionHeaders(response);
            System.out.println(response.toString());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("login/verify")
    public void verifyLoginRequest() {
        try {
            String dsid = "BB7293C50244F95F:O8PQBMxkxLuRh5JD4YzjAyYuY6E=";

            HttpGet httpGet = new HttpGet(String.format("https://online.swedbank.se/TDE_DAP_Portal_REST_WEB/api/v5/identification/bankid/mobile/verify?dsid=%s", dsid));
            CloseableHttpClient client = HttpClients.createDefault();

            setCommonHeaders(httpGet);
            setSessionHeaders(httpGet);

            CloseableHttpResponse response = client.execute(httpGet);
            saveSessionHeaders(response);
            System.out.println(response.toString());

            client.close();
            getAuthenticatedProfile();
            postAuthenticatedProfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAuthenticatedProfile() {
        try {
            String dsid = "BB7293C50244F95F:O8PQBMxkxLuRh5JD4YzjAyYuY6E=";
            HttpGet httpGet = new HttpGet(String.format("https://online.swedbank.se/TDE_DAP_Portal_REST_WEB/api/v5/profile/?dsid=%s", dsid));
            CloseableHttpClient client = HttpClients.createDefault();

            setCommonHeaders(httpGet);
            setSessionHeaders(httpGet);

            CloseableHttpResponse response = client.execute(httpGet);
            String json = convertStreamToString(response.getEntity().getContent());
            System.out.println(json);

            JSONArray array = new JSONArray(json);
            JSONObject object2 = array.getJSONObject(0);
            JSONObject object3 = object2.getJSONObject("privateProfile");
            id = object3.getString("id");

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postAuthenticatedProfile() {
        try {
            String dsid = "BB7293C50244F95F:O8PQBMxkxLuRh5JD4YzjAyYuY6E=";
            HttpPost httpPost = new HttpPost(String.format("https://online.swedbank.se/TDE_DAP_Portal_REST_WEB/api/v5/profile/%s?dsid=%s", id, dsid));
            CloseableHttpClient client = HttpClients.createDefault();

            setCommonHeaders(httpPost);
            setSessionHeaders(httpPost);

            CloseableHttpResponse response = client.execute(httpPost);
            String json = convertStreamToString(response.getEntity().getContent());
            System.out.println(json);
            System.out.println("!!Authenticated!!");

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/holdings")
    public String getHoldings() {
        try {
            String dsid = "BB7293C50244F95F:O8PQBMxkxLuRh5JD4YzjAyYuY6E=";

            HttpGet httpGet = new HttpGet(String.format("https://online.swedbank.se/TDE_DAP_Portal_REST_WEB/api/v5/portfolio/holdings?dsid=%s", dsid));
            CloseableHttpClient client = HttpClients.createDefault();

            setCommonHeaders(httpGet);
            setSessionHeaders(httpGet);

            CloseableHttpResponse response = client.execute(httpGet);

            String json = convertStreamToString(response.getEntity().getContent());
            System.out.println(json);
            System.out.println("\n" + "\n" + "\n");
            client.close();
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Tremendously bad work. Sad!";
    }

    private void saveSessionHeaders(CloseableHttpResponse response) {
        for (Header header : response.getAllHeaders()) {
            System.out.println("Name: " + header.getName() + "\n" + "Value: " + header.getValue());
            if (header.getValue().contains("JSESSIONID")) {
                jsessionid = header.getValue().substring(0, 49);
                System.out.println("WOHO! " + jsessionid);
            }
            if (header.getValue().contains("SWBTC")) {
                swbtc = header.getValue().substring(0, 100);
                System.out.println("WOHO! " + swbtc);
            }
        }
    }

    private void setSessionHeaders(HttpRequest request) {
        request.setHeader("Cookie", swbtc + jsessionid);
    }

    private void setCommonHeaders(HttpRequest request) {
        String authorization = "QjdkWkhRY1k3OFZSVno5bDoxNTc5NzA4MTcyMDg4";

        request.setHeader("Authorization", String.format("%s", authorization));
        request.setHeader("Accept", "application/json, text/plain, */*");
        request.setHeader("X-Client", "fdp-nib/164.1.0");
        request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Cache-Control", "no-cache");
        request.setHeader("Host", "online.swedbank.se");
        request.setHeader("Accept-Encoding", "gzip, deflate, br");
        request.setHeader("Connection", "keep-alive");
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
