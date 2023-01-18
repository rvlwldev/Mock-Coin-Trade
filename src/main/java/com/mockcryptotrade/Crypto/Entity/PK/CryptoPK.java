package com.mockcryptotrade.Crypto.Entity.PK;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoPK implements Serializable {
    private String CryptoId;
    private String CryptoMarket;

//    public CryptoID(String CryptoId, String CryptoMarket) {
//        this.CryptoId = CryptoId;
//        this.CryptoMarket = CryptoMarket;
//    }
}
