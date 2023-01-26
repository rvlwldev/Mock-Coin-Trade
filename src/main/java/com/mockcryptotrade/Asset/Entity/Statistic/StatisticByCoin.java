package com.mockcryptotrade.Asset.Entity.Statistic;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity(name = "MOCK_SOLD_LOG")
@Table(name = "MOCK_SOLD_LOG")
@Data
@NoArgsConstructor
public class StatisticByCoin {

    @Id
    @Column(name = "ACCOUNT_ID")
    String accountID;

    @Column(name = "CRYPTO_ID")
    String cryptoID;

    @Column(name = "CRYPTO_MARKET")
    String cryptoMarket;

    @Column(name = "SOLD_CRYPTO_COUNT")
    double soldCryptoCount;

    @Column(name = "SOLD_AMOUNT")
    double soldAmount;

    @Column(name = "PROFIT_MONEY")
    double profitMoney;

    @Column(name = "RATE_OF_RETURN")
    double rateOfReturn;

    @Column(name = "SOLD_DATE")
    LocalDateTime soldDate;

    @Column(name = "DATE_RANGE")
    String dateRange;
}
