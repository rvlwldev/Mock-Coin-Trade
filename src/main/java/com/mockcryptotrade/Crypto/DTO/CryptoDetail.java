package com.mockcryptotrade.Crypto.DTO;

import lombok.Data;


@Data
public class CryptoDetail {
    /* https://docs.upbit.com/reference/ticker현재가-정보 */

    private String CryptoId;
    private String CryptoMarket;
    private String fullNameKO;
    private double tradePrice; /*현재가*/
    private double signedChangeRate; /*부호가 있는 변화율*/
    private double accTradePrice24h; /* 거래량 */

    public CryptoDetail (CryptoTicker ticker) {
        this.CryptoId = ticker.getMarket().split("-")[1];
        this.CryptoMarket = ticker.getMarket().split("-")[0];
        this.tradePrice = ticker.getTrade_price();
        this.signedChangeRate = (double) Math.round(ticker.getSigned_change_rate() * 100 * 100) / 100;
        this.accTradePrice24h = ticker.getAcc_trade_price_24h() / 1000000;
    }
}
