package com.mockcryptotrade.Domain.Crypto;

import lombok.Data;


@Data
public class CryptoDetail {
    /* https://docs.upbit.com/reference/ticker현재가-정보 */

    private String CryptoId;
    private String CryptoMarket;
    private String fullNameKO;
    private double tradePrice; /*현재가*/
    private double signedChangeRate; /*부호가 있는 변화율*/
    private int accTradePrice24h; /* 거래량 */
}
