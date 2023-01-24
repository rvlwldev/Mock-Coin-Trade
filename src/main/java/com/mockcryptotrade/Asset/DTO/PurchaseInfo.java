package com.mockcryptotrade.Asset.DTO;

import lombok.Data;

@Data
public class PurchaseInfo {
    private String CryptoId;
    private String CryptoMarket;
    private double buyPrice;
    private double buyCount;
}
