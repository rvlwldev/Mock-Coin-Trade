package com.mockcryptotrade.Domain.Crypto.PK;

import lombok.Data;

import java.io.Serializable;

@Data
public class CryptoID implements Serializable {
    private String CryptoId;
    private String CryptoMarket;
}
