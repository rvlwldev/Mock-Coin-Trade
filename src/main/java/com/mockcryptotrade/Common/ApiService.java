package com.mockcryptotrade.Common;

import com.mockcryptotrade.Domain.Crypto.CryptoInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.mockcryptotrade.Common.Enum.API_URL.ALL_CRYPTO_INFO_API;

@Service
public class ApiService {

    public List<CryptoInfo> initializeCryptoNames() throws IOException {
        HttpURLConnection connection = getCryptoListAPIConnection();

        StringBuilder StringBuilder = new StringBuilder();
        BufferedReader bufferedReader;
        String readLine;

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            while ((readLine = bufferedReader.readLine()) != null) {
                StringBuilder.append(readLine)
                        .append("\n");
            }
        }

        return toCryptoEntity(StringBuilder);
    }

    private HttpURLConnection getCryptoListAPIConnection() {
        try {
            URL url = new URL(ALL_CRYPTO_INFO_API.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(3000);
            connection.setRequestProperty("Accept", "application/json;");

            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<CryptoInfo> toCryptoEntity(StringBuilder stringBuffer) {
        List<CryptoInfo> cryptoList = new ArrayList<>();

        JSONArray array = new JSONArray(stringBuffer.toString());

        for (Object cryptoInfo : array) {
            JSONObject info = (JSONObject) cryptoInfo;

            CryptoInfo entity = getCryptoInfoByApiData(info);
            cryptoList.add(entity);

//            System.out.println("마켓 : " + entity.getCryptoMarket() + " 약어 : " + entity.getCryptoId() + " 이름 : " + entity.getFullNameKO());
        }

        return cryptoList;
    }

    private CryptoInfo getCryptoInfoByApiData(JSONObject info) {
        CryptoInfo cryptoInfo = new CryptoInfo();

        cryptoInfo.setCryptoId(getCryptoID(info));
        cryptoInfo.setCryptoMarket(getCryptoMarket(info));
        cryptoInfo.setFullNameKO(info.get("korean_name").toString());
        cryptoInfo.setFullNameEN(info.get("english_name").toString());

        // TODO : 추후 한화마켓이 아닌 다른 마켓도 지원할때 삭제 요망
        cryptoInfo.setUseYn(cryptoInfo.getCryptoMarket().equals("KRW") ? 1 : 0);

        return cryptoInfo;
    }

    private String getCryptoID(JSONObject info) {
        return info.get("market")
                .toString()
                .split("-")[1];
    }

    private String getCryptoMarket(JSONObject info) {
        return info.get("market")
                .toString()
                .split("-")[0];
    }
}
