package com.mockcryptotrade.Asset.Entity.Statistic;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "STATISTIC")
@Table(name = "MOCK_SOLD_LOG")
@Data
@NoArgsConstructor
public class StatisticByDates {
    @Id
    @Column(name = "ACCOUNT_ID")
    private String accountID;

    @Column(name = "CRYPTO_ID")
    private String cryptoID;

    @Column(name = "SOLD_DATE")
    private String mockDate;

    @Column(name = "SOLD_CRYPTO_COUNT")
    private double soldCryptoCount;

    @Column(name = "SOLD_AMOUNT")
    private double soldAmount;

    @Column(name = "PROFIT_MONEY")
    private double profitMoney;

    @Column(name = "RATE_OF_RETURN")
    private double rateOfReturn;
}
