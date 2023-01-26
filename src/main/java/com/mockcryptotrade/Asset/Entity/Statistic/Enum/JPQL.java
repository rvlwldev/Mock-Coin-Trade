package com.mockcryptotrade.Asset.Entity.Statistic.Enum;

public enum JPQL {

    StatisticByCoin("SELECT A.cryptoID  AS CRYPTO_ID\n" +
            ",A.cryptoMarket                AS CRYPTO_MARKET    \n" +
            ",SUM(soldCryptoCount)          AS SOLD_CRYPTO_COUNT\n" +
            ",SUM(soldAmount)               AS SOLD_AMOUNT      \n" +
            ",SUM(profitMoney)              AS PROFIT_MONEY     \n" +
            ",ROUND( AVG(rateOfReturn) / COUNT(A.cryptoID) * 100 / 100 , 2) AS RATE_OF_RETURN\n" +
            ",CONCAT(DATE_FORMAT( MIN(A.soldDate) , '%Y-%m-%d')\n" +
            ",'~'\n" +
            ",DATE_FORMAT( MAX(A.soldDate) , '%Y-%m-%d')) AS DATE_RANGE\n" +
            "FROM MOCK_SOLD_LOG A  \n" +
            "WHERE accountID = :id \n" +
            "GROUP BY A.accountID  \n" +
            ",A.cryptoID   \n" +
            ",A.cryptoMarket");

    private String sql;

    JPQL(String sql) {
        this.sql = sql;
    }
}
