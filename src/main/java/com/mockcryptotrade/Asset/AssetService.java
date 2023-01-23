package com.mockcryptotrade.Asset;

import com.mockcryptotrade.Asset.DTO.AssetDetail;
import com.mockcryptotrade.Asset.DTO.PurchaseInfo;
import com.mockcryptotrade.Asset.Entity.AssetPurchase;
import com.mockcryptotrade.Asset.Entity.AssetSelect;
import com.mockcryptotrade.Asset.Entity.PK.AssetPK;
import com.mockcryptotrade.Asset.Repository.AssetPurchaseRepo;
import com.mockcryptotrade.Crypto.CryptoService;
import com.mockcryptotrade.Crypto.DTO.CryptoDetail;
import com.mockcryptotrade.Crypto.Entity.Crypto;
import com.mockcryptotrade.Crypto.Repository.CryptoRepo;
import com.mockcryptotrade.ExternalAPI.ApiService;
import com.mockcryptotrade.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    CryptoRepo cryptoRepo;

    public AssetPurchase getAssetPurchase(PurchaseInfo purchaseDTO) {
        AssetPK pk = new AssetPK(loginService.getUserID(), purchaseDTO.getCoinId(), purchaseDTO.getCoinMarket());
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

    public List<AssetDetail> getUsersAssetDetails(List<AssetSelect> selects) {
        List<Crypto> cryptos = new ArrayList<>();

        for (AssetSelect select : selects) cryptos.add(select.getCrypto());

        String param = cryptoService.getParamValueForCryptoDetails(cryptos);
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

        for (AssetDetail detail : detailList) {
            rate += detail.getRateOfReturn();
        }

        return Math.round(rate / detailList.size() * 100) / 100.0;
    }

    public double getTotalEarnMoney(List<AssetDetail> detailList, double rateOfReturn) {
        double money = 0;
        for (AssetDetail detail : detailList) {
            money += detail.getAvgMoneyValue();
        }
        return Math.round((money * rateOfReturn) / 100.0 * 100) / 100.0;
    }

}
