package com.mockcryptotrade.Asset.DTO;

import com.mockcryptotrade.Asset.Entity.AssetSelect;
import lombok.Data;

@Data
public class AssetDetail {

    private String accountID;
    private String cryptoID;
    private String cryptoMarket;
    private String fullNameKO;
    private double avgMoneyValue;
    private double cryptoCount;
    private double nowPrice;
    private double pricePerOne; // 평단가
    private double rateOfReturn;

    public AssetDetail(AssetSelect select) {
        this.accountID = select.getAccountID();
        this.cryptoID = select.getCryptoID();
        this.cryptoMarket = select.getCryptoMarket();
        this.avgMoneyValue = select.getAvgMoneyValue();
        this.cryptoCount = Math.round(select.getCryptoCount() * 100) / 100.0;
        this.fullNameKO = select.getCrypto().getFullNameKO();
        this.pricePerOne = Math.round( select.getAvgMoneyValue() / select.getCryptoCount() * 100) / 100.0;
    }

    public void setRateOfReturn(double rateOfReturn) {
        this.rateOfReturn = rateOfReturn;
    }

    public void setNowPrice(double nowPrice) {
        this.nowPrice = Math.round(nowPrice * 100) / 100.0;
    }
}
