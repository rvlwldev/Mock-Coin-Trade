package com.mockcryptotrade.Asset.Entity;

import com.mockcryptotrade.Asset.Entity.PK.AssetPK;
import com.mockcryptotrade.Crypto.Entity.Crypto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MOCK_ASSET")
@IdClass(AssetPK.class)
public class AssetSelect {
    @Id
    @Column(name = "ACCOUNT_ID")
    private String accountID;

    @Id
    @Column(name = "CRYPTO_ID", insertable = false, updatable = false)
    private String cryptoID;

    @Id
    @Column(name = "CRYPTO_MARKET", insertable = false, updatable = false)
    private String cryptoMarket;

    @ManyToOne(targetEntity = Crypto.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "CRYPTO_ID"),
            @JoinColumn(name = "CRYPTO_MARKET")
    })
    private Crypto crypto;

    @Column(name = "CRYPTO_COUNT")
    private double cryptoCount;

    @Column(name = "AVG_BUY_MONEY_VALUE")
    private double avgMoneyValue;

    @Column(name = "FIRST_BUY_DATE")
    private LocalDateTime firstBuyDate;

    @Column(name = "LAST_ADDITIONAL_DATE")
    private LocalDateTime lastAddDate;
}
