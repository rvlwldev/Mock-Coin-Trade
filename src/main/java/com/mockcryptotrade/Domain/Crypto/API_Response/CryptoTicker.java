package com.mockcryptotrade.Domain.Crypto.API_Response;

import lombok.Data;

@Data
public class CryptoTicker {
    private String market;
    private double trade_price; /*현재가*/
    private double signed_change_rate; /*부호가 있는 변화율*/
    private double acc_trade_price_24h; /* 거래량 */
}
