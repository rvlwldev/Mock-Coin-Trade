package com.mockcryptotrade.Asset.Entity;

import com.mockcryptotrade.Asset.DTO.SellInfo;
import com.mockcryptotrade.Asset.Entity.PK.AssetLogPK;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MOCK_SOLD_LOG")
@IdClass(AssetLogPK.class)
@NoArgsConstructor
public class AssetLog {
    @Id
    @Column(name = "ACCOUNT_ID")
    String accountID;

    @Id
    @Column(name = "CRYPTO_ID")
    String cryptoID;

    @Id
    @Column(name = "CRYPTO_MARKET")
    String cryptoMarket;

    @Id
    @Column(name = "SOLD_DATE")
    LocalDateTime soldDate;

    @Column(name = "SOLD_CRYPTO_COUNT")
    double soldCryptoCount;

    @Column(name = "SOLD_AMOUNT")
    double soldAmount;

    @Column(name = "PROFIT_MONEY")
    double profitMoney;

    @Column(name = "RATE_OF_RETURN")
    double rateOfReturn;

    public AssetLog(SellInfo sellInfo, AssetPurchase purchase) {
        this.accountID = purchase.getAccountID();
        this.cryptoID = purchase.getCryptoID();
        this.cryptoMarket = purchase.getCryptoMarket();
        this.soldDate = LocalDateTime.now();

        this.soldCryptoCount = sellInfo.getSellCount();
        this.soldAmount = sellInfo.getSellPrice();
        this.profitMoney =  sellInfo.getSellPrice() - purchase.getAvgMoneyValue();
        this.rateOfReturn = sellInfo.getRateOfReturn();
    }
}
