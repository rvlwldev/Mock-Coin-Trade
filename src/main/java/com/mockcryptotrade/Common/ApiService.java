package com.mockcryptotrade.Common;

import com.mockcryptotrade.Domain.Crypto.CryptoInit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static com.mockcryptotrade.Common.Enum.API_URL.ALL_CRYPTO_INFO_API;

@Service
public class ApiService {

    @Autowired
    CommonService common;

    public List<CryptoInit> initializeCryptoNames() throws IOException {
        HttpURLConnection connection = common.getAPIConnection(ALL_CRYPTO_INFO_API.toString());
        String apiInfo = common.toJSONStringByConnection(connection);
        return toCryptoInfoEntity(apiInfo);
    }

    private List<CryptoInit> toCryptoInfoEntity(String apiInfo) {
        List<CryptoInit> cryptoList = new ArrayList<>();

        JSONArray array = new JSONArray(apiInfo);

        for (Object cryptoInfo : array) {
            JSONObject info = (JSONObject) cryptoInfo;

            CryptoInit entity = getCryptoInfoByApiData(info);
            cryptoList.add(entity);
        }

        return cryptoList;
    }

    private CryptoInit getCryptoInfoByApiData(JSONObject info) {
        CryptoInit cryptoInit = new CryptoInit();

        cryptoInit.setCryptoId(getCryptoID(info));
        cryptoInit.setCryptoMarket(getCryptoMarket(info));
        cryptoInit.setFullNameKO(info.get("korean_name").toString());
        cryptoInit.setFullNameEN(info.get("english_name").toString());

        // TODO : 추후 한화마켓이 아닌 다른 마켓도 지원할때 삭제 요망
        cryptoInit.setUseYn(cryptoInit.getCryptoMarket().equals("KRW") ? 1 : 0);

        return cryptoInit;
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
