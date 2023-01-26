package com.mockcryptotrade.Asset.DTO;

import lombok.Data;

@Data
public class SellInfo {
    String cryptoId;
    String cryptoMarket;
    double pricePerOne;
    double sellCount;
    double sellPrice;
    double profitMoney;
    double rateOfReturn;
}
