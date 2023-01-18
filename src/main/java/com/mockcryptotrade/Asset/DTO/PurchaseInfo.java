package com.mockcryptotrade.Asset.DTO;

import lombok.Data;

@Data
public class PurchaseInfo {
    private String coinId;
    private String coinMarket;
    private double buyPrice;
    private double buyCount;
}
