package com.mockcryptotrade.Service;

import com.mockcryptotrade.Common.CommonService;
import com.mockcryptotrade.Domain.Crypto.CryptoDetail;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;

import static com.mockcryptotrade.Common.Enum.API_URL.CRYPTO_DETAIL_INFO_API;


@Service
public class CryptoService {

    @Autowired
    CommonService common;

    public CryptoDetail getDetails(String id, String market, String fullNameKO) throws IOException {
        CryptoDetail detail = new CryptoDetail();

        HttpURLConnection connection = common.getAPIConnection(CRYPTO_DETAIL_INFO_API.toString() + market + "-" + id);
        String info = common.toJSONStringByConnection(connection);

        try {
            JSONObject details = (JSONObject) new JSONArray(info).get(0);

            double trade_price = Double.parseDouble(details.get("trade_price").toString());
            double signed_change_rate = Double.parseDouble(details.get("signed_change_rate").toString());
            double acc_trade_price_24h = Double.parseDouble(details.get("acc_trade_price_24h").toString());

            trade_price = Math.round(trade_price * 100) / 100.0;
            signed_change_rate = Math.round(signed_change_rate * 10000) / 100.0;
            acc_trade_price_24h = Math.round(acc_trade_price_24h / 100000);

            detail.setCryptoId(id);
            detail.setCryptoMarket(market);
            detail.setFullNameKO(fullNameKO);
            detail.setTradePrice(trade_price);
            detail.setSignedChangeRate(signed_change_rate);
            detail.setAccTradePrice24h((int) acc_trade_price_24h);

            return detail;
        } catch (Exception e) {
            return null;
        }

    }

}
