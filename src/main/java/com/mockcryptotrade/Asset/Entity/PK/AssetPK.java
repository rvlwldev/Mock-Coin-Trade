package com.mockcryptotrade.Asset.Entity.PK;

import lombok.Data;

import java.io.Serializable;

@Data
public class AssetPK implements Serializable {
    private String accountID;
    private String cryptoID;
    private String cryptoMarket;

    public AssetPK(String accountID, String cryptoID, String cryptoMarket) {
        this.accountID = accountID;
        this.cryptoID = cryptoID;
        this.cryptoMarket = cryptoMarket;
    }

    public AssetPK() {

    }
}
