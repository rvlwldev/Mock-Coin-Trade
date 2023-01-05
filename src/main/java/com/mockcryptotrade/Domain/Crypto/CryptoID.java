package com.mockcryptotrade.Domain.Crypto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CryptoID implements Serializable {
    private String CryptoId;
    private String CryptoMarket;
}
