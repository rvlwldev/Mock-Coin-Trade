package com.mockcryptotrade.Domain.Crypto;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CRYPTO")
@IdClass(CryptoID.class)
public class Crypto {

    @Id
    @Column(name = "CRYPTO_ID")
    private String CryptoId;

    @Id
    @Column(name = "CRYPTO_MARKET")
    private String CryptoMarket;

    @Column(name = "FULL_NAME_KO")
    private String fullNameKO;

    @Column(name = "FULL_NAME_EN")
    private String fullNameEN;

    @Column(name = "USE_YN")
    @ColumnDefault("1")
    private int useYn;

}