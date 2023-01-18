package com.mockcryptotrade.ExternalAPI;

import com.mockcryptotrade.Crypto.DTO.CryptoInit;
import com.mockcryptotrade.Crypto.DTO.CryptoTicker;
import com.mockcryptotrade.Crypto.Entity.Crypto;
import com.mockcryptotrade.Crypto.DTO.CryptoDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static com.mockcryptotrade.ExternalAPI.Enum.API_URL.ALL_CRYPTO_INFO_API;
import static com.mockcryptotrade.ExternalAPI.Enum.API_URL.CRYPTO_DETAIL_INFO_API;

@Service
public class ApiService {

    public List<CryptoInit> getCryptoInitList() {
        WebClient webclient = WebClient.create(ALL_CRYPTO_INFO_API.toString());

        List<CryptoInit> result = webclient.get()
                .retrieve()
                .bodyToFlux(CryptoInit.class)
                .collectList()
                .block();

        return result;
    }

    public List<CryptoDetail> getCryptoDetailList(List<Crypto> cryptos, String param) {
        List<CryptoDetail> detailList = new ArrayList<>();

        WebClient webclient = WebClient.create(CRYPTO_DETAIL_INFO_API + param);

        List<CryptoTicker> result = webclient.get()
                .retrieve()
                .bodyToFlux(CryptoTicker.class)
                .collectList()
                .block();

        for (CryptoTicker ticker : result) detailList.add(new CryptoDetail(ticker));

        for (int i = 0; i < result.size(); i++) {
            detailList.get(i).setFullNameKO(cryptos.get(i).getFullNameKO());
        }

        return detailList;
    }
}
