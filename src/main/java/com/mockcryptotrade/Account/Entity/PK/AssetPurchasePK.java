package com.mockcryptotrade.Account.Entity.PK;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssetPurchasePK implements Serializable {
    private String accountID;
    private String cryptoID;
    private String cryptoMarket;

    public AssetPurchasePK(String accountID, String cryptoID, String cryptoMarket){
        this.accountID = accountID;
        this.cryptoID = cryptoID;
        this.cryptoMarket = cryptoMarket;
    }

    public AssetPurchasePK() {

    }
}
