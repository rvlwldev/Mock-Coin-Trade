package com.mockcryptotrade.Common;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class CommonService {

    public HttpURLConnection getAPIConnection(String url) {
        try {
            URL apiURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(3000);
            connection.setRequestProperty("Accept", "application/json;");

            return connection;
        } catch (Exception e) {
            System.err.println("\nAPI Connection 불러오기 실패!\n");
            e.printStackTrace();
            return null;
        }
    }

    public String toJSONStringByConnection(HttpURLConnection connection) throws IOException {
        StringBuilder stringbuilder = new StringBuilder();
        BufferedReader bufferedReader;
        String readLine;

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            while ((readLine = bufferedReader.readLine()) != null) {
                stringbuilder.append(readLine)
                        .append("\n");
            }
        }

        return stringbuilder.toString();
    }

}
