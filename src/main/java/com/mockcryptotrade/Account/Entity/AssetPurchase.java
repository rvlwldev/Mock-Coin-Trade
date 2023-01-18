package com.mockcryptotrade.Account.Entity;

import com.mockcryptotrade.Account.Entity.PK.AssetPurchasePK;
import com.mockcryptotrade.Asset.DTO.PurchaseInfo;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "MOCK_ASSET")
@IdClass(AssetPurchasePK.class)
public class AssetPurchase {

    @Id
    @Column(name = "ACCOUNT_ID")
    private String accountID;

    @Id
    @Column(name = "CRYPTO_ID")
    private String cryptoID;

    @Id
    @Column(name = "CRYPTO_MARKET")
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
        this.cryptoID = dto.getCoinId();
        this.cryptoMarket = dto.getCoinMarket();
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
