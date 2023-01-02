package com.mockcryptotrade.Domain.Crypto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class CryptoId implements Serializable {
    @Column(name = "CRYPTO_ID")
    private String CryptoID;

    @Column(name = "CRYPTO_MARKET")
    private String CryptoMarket;
}
