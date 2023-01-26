package com.mockcryptotrade.Asset.Entity.PK;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AssetLogPK implements Serializable {

    String accountID;
    String cryptoID;
    String cryptoMarket;
    LocalDateTime soldDate;

    public AssetLogPK(String accountID, String cryptoID, String cryptoMarket) {
        this.accountID = accountID;
        this.cryptoID = cryptoID;
        this.cryptoMarket = cryptoMarket;
        this.soldDate = LocalDateTime.now();
    }

    public AssetLogPK() {

    }
}
