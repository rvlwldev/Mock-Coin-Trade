package com.mockcryptotrade.Asset.Entity;

import com.mockcryptotrade.Asset.DTO.PurchaseInfo;
import com.mockcryptotrade.Asset.DTO.SellInfo;
import com.mockcryptotrade.Asset.Entity.PK.AssetPK;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "MOCK_ASSET")
@IdClass(AssetPK.class)
@NoArgsConstructor
public class AssetPurchase {

    @Id
    @Column(name = "ACCOUNT_ID")
    private String accountID;

    @Id
    @Column(name = "CRYPTO_ID", insertable = false, updatable = false)
    private String cryptoID;

    @Id
    @Column(name = "CRYPTO_MARKET", insertable = false, updatable = false)
    private String cryptoMarket;

    @Column(name = "CRYPTO_COUNT")
    private double cryptoCount;

    @Column(name = "AVG_BUY_MONEY_VALUE")
    private double avgMoneyValue;

    @Column(name = "FIRST_BUY_DATE")
    private LocalDateTime firstBuyDate;

    @Column(name = "LAST_ADDITIONAL_DATE")
    private LocalDateTime lastAddDate;

    public AssetPurchase(String accountID, PurchaseInfo dto) {
        this.accountID = accountID;
        this.cryptoID = dto.getCryptoId();
        this.cryptoMarket = dto.getCryptoMarket();
        this.cryptoCount = dto.getBuyCount();
        this.avgMoneyValue = dto.getBuyPrice();
        this.firstBuyDate = LocalDateTime.now();
        this.lastAddDate = null;
    }

    public void addPrice(double addedPrice) {
        this.avgMoneyValue += addedPrice;
    }

    public void addCount(double addedCount) {
        this.cryptoCount += addedCount;
    }
}
