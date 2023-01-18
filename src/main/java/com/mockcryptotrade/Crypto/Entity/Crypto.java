package com.mockcryptotrade.Crypto.Entity;

import com.mockcryptotrade.Crypto.Entity.PK.CryptoPK;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CRYPTO")
@IdClass(CryptoPK.class)
@NoArgsConstructor
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

    public Crypto(String CryptoId, String CryptoMarket) {
        this.CryptoId = CryptoId;
        this.CryptoMarket = CryptoMarket;
    }

    public void setCryptoId(String initData) {
        CryptoId = initData.split("-")[1];
    }

    public void setCryptoMarket(String initData) {
        CryptoMarket = initData.split("-")[0];
    }
}
