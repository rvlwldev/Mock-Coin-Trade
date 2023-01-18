package com.mockcryptotrade.Crypto.Service;

import com.mockcryptotrade.Crypto.Entity.Crypto;
import com.mockcryptotrade.Crypto.DTO.CryptoInit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CryptoService {

    public List<Crypto> convertInitCryptoToEntity(List<CryptoInit> initList) {
        List<Crypto> list = new ArrayList<>();

        for (CryptoInit init : initList) {
            Crypto crypto = new Crypto();

            crypto.setCryptoId(init.getMarket());
            crypto.setCryptoMarket(init.getMarket());
            crypto.setFullNameKO(init.getKorean_name());
            crypto.setFullNameEN(init.getEnglish_name());

            // TODO: 현재는 한화만을 기준으로... 확장 시 수정요망
            crypto.setUseYn(crypto.getCryptoMarket().equals("KRW") ? 1 : 0);

            list.add(crypto);
        }

        return list;
    }

    public String getParamValueForCryptoDetails(List<Crypto> cryptos) {
        StringBuilder paramBuilder = new StringBuilder();
        for (Crypto c : cryptos) {
            paramBuilder.append(c.getCryptoMarket())
                    .append("-")
                    .append(c.getCryptoId())
                    .append(",");
        }

        return paramBuilder.substring(0, paramBuilder.length() - 1);
    }

}