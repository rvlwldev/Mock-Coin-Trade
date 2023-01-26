package com.mockcryptotrade.Asset;

import com.mockcryptotrade.Asset.DTO.AssetDetail;
import com.mockcryptotrade.Asset.DTO.PurchaseInfo;
import com.mockcryptotrade.Asset.DTO.SellInfo;
import com.mockcryptotrade.Asset.Entity.AssetPurchase;
import com.mockcryptotrade.Asset.Entity.AssetSelect;
import com.mockcryptotrade.Asset.Entity.PK.AssetPK;
import com.mockcryptotrade.Asset.Repository.AssetPurchaseRepo;
import com.mockcryptotrade.Crypto.CryptoService;
import com.mockcryptotrade.Crypto.DTO.CryptoDetail;
import com.mockcryptotrade.Crypto.Entity.Crypto;
import com.mockcryptotrade.ExternalAPI.ApiService;
import com.mockcryptotrade.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    LoginService loginService;

    @Autowired
    ApiService api;

    @Autowired
    CryptoService cryptoService;

    @Autowired
    AssetPurchaseRepo assetPurchaseRepo;

    @PersistenceContext
    private EntityManager entityManager;

    public AssetPurchase getAssetPurchase(PurchaseInfo purchaseDTO) {
        AssetPK pk = new AssetPK(loginService.getUserID(), purchaseDTO.getCryptoId(), purchaseDTO.getCryptoMarket());
        Optional<AssetPurchase> reserves = assetPurchaseRepo.findById(pk);

        AssetPurchase target;

        if (reserves.isEmpty()) target = new AssetPurchase(loginService.getUserID(), purchaseDTO);
        else {
            target = reserves.get();

            target.addPrice(purchaseDTO.getBuyPrice());
            target.addCount(purchaseDTO.getBuyCount());
        }

        return target;
    }

    public List statCoin() {
        return entityManager.createQuery("SELECT A.cryptoID  AS CRYPTO_ID\n" +
                        ",A.cryptoMarket                             AS CRYPTO_MARKET    \n" +
                        ",SUM(soldCryptoCount)                       AS SOLD_CRYPTO_COUNT\n" +
                        ",SUM(soldAmount)                            AS SOLD_AMOUNT      \n" +
                        ",SUM(profitMoney)                           AS PROFIT_MONEY     \n" +
                        ",ROUND( AVG(rateOfReturn) / COUNT(A.cryptoID) * 100 / 100 , 2) AS RATE_OF_RETURN\n" +
                        ",CASE WHEN MIN(SOLD_DATE) = MAX(SOLD_DATE) \n" +
                        "      THEN DATE_FORMAT( MAX(SOLD_DATE) , '%Y-%m-%d') \n" +
                        "      ELSE" +
                        "      CONCAT(DATE_FORMAT( MIN(A.soldDate) , '%Y-%m-%d')\n" +
                        "            ,'~'\n" +
                        "            ,DATE_FORMAT( MAX(A.soldDate) , '%Y-%m-%d')) " +
                        "       END AS DATE_RANGE\n" +
                        "FROM MOCK_SOLD_LOG A  \n" +
                        "WHERE accountID = :id \n" +
                        "GROUP BY A.accountID  \n" +
                        ",A.cryptoID   \n" +
                        ",A.cryptoMarket")
                .setParameter("id", loginService.getUserID())
                .getResultList();
    }

    public List statDay() {
        return entityManager.createQuery("SELECT accountID                      AS ACCOUNT_ID        \n" +
                        ",DATE_FORMAT(A.mockDate, '%Y-%m-%d')                           AS MOCK_DATE         \n" +
                        ",SUM(soldCryptoCount)                                          AS SOLD_CRYPTO_COUNT \n" +
                        ",SUM(soldAmount)                                               AS SOLD_AMOUNT       \n" +
                        ",SUM(profitMoney)                                              AS PROFIT_MONEY      \n" +
                        ",ROUND( AVG(rateOfReturn) / COUNT(A.cryptoID) * 100 / 100 , 2) AS RATE_OF_RETURN    \n" +
                        "  FROM STATISTIC A      \n" +
                        " WHERE ACCOUNT_ID = :id \n" +
                        " GROUP BY DATE_FORMAT(A.mockDate, '%Y-%m-%d')\n" +
                        "         ,accountID\n" +
                        " ORDER BY 1")
                .setParameter("id", loginService.getUserID())
                .getResultList();
    }

    public List statMonth() {
        return entityManager.createQuery("SELECT accountID                      AS ACCOUNT_ID        \n" +
                        ",DATE_FORMAT(A.mockDate, '%Y-%m')                              AS MOCK_DATE         \n" +
                        ",SUM(soldCryptoCount)                                          AS SOLD_CRYPTO_COUNT \n" +
                        ",SUM(soldAmount)                                               AS SOLD_AMOUNT       \n" +
                        ",SUM(profitMoney)                                              AS PROFIT_MONEY      \n" +
                        ",ROUND( AVG(rateOfReturn) / COUNT(A.cryptoID) * 100 / 100 , 2) AS RATE_OF_RETURN    \n" +
                        "  FROM STATISTIC A      \n" +
                        " WHERE ACCOUNT_ID = :id \n" +
                        " GROUP BY DATE_FORMAT(A.mockDate, '%Y-%m')\n" +
                        "         ,accountID\n" +
                        " ORDER BY 1")
                .setParameter("id", loginService.getUserID())
                .getResultList();
    }

    public void reset() {
        entityManager.createQuery("DELETE FROM STATISTIC WHERE ACCOUNT_ID = :id")
                .setParameter("id", loginService.getUserID()).executeUpdate();
    }

    public AssetPurchase getAssetPurchase(SellInfo sellInfoDTO) {
        AssetPK pk = new AssetPK(loginService.getUserID(), sellInfoDTO.getCryptoId(), sellInfoDTO.getCryptoMarket());
        AssetPurchase info = assetPurchaseRepo.findById(pk).get();

        info.setCryptoCount(info.getCryptoCount() - sellInfoDTO.getSellCount());

        return info;
    }

    public List<AssetDetail> getUsersAssetDetails(List<AssetSelect> selects) {
        List<Crypto> cryptos = new ArrayList<>();

        for (AssetSelect select : selects) cryptos.add(select.getCrypto());

        String param = cryptoService.getParamValueForCryptoDetails(cryptos);

        if (param == null) return null;

        List<CryptoDetail> cryptoDetails = api.getCryptoDetailList(cryptos, param);

        List<AssetDetail> assetDetails = new ArrayList<>();
        for (int i = 0; i < cryptoDetails.size(); i++) {
            CryptoDetail nowDetail = cryptoDetails.get(i);
            AssetSelect select = selects.get(i);

            // 평단가
            double pricePerOne = select.getAvgMoneyValue() / select.getCryptoCount();

            // 현재가
            double nowPrice = nowDetail.getTradePrice();

            AssetDetail newAssetDetail = new AssetDetail(select);
            newAssetDetail.setRateOfReturn(getRateOfReturn(pricePerOne, nowPrice));
            newAssetDetail.setNowPrice(nowPrice);

            assetDetails.add(newAssetDetail);
        }

        return assetDetails;
    }

    public double getRateOfReturn(double nowPrice, double buyPrice) {
        double rateOfReturn = (buyPrice - nowPrice) / nowPrice * 100;
        return Math.round(rateOfReturn * 100) / 100.0;
    }

    public double getTotalRateOfReturn(List<AssetDetail> detailList) {
        double rate = 0;

        try {
            for (AssetDetail detail : detailList) rate += detail.getRateOfReturn();

            return Math.round(rate / detailList.size() * 100) / 100.0;
        } catch (Exception e) {
            return rate;
        }
    }

    public double getTotalEarnMoney(List<AssetDetail> detailList, double rateOfReturn) {
        double money = 0;

        try {
            for (AssetDetail detail : detailList) money += detail.getAvgMoneyValue();

            return Math.round((money * rateOfReturn) / 100.0 * 100) / 100.0;
        } catch (Exception e) {
            return money;
        }

    }

}
