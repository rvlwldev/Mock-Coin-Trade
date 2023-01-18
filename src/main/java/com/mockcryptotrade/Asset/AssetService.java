package com.mockcryptotrade.Asset;

import com.mockcryptotrade.Asset.Entity.PK.AssetPK;
import com.mockcryptotrade.Asset.DTO.PurchaseInfo;
import com.mockcryptotrade.Asset.Entity.AssetPurchase;
import com.mockcryptotrade.Asset.Repository.AssetPurchaseRepo;
import com.mockcryptotrade.Crypto.Repository.CryptoRepo;
import com.mockcryptotrade.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    LoginService loginService;

    @Autowired
    AssetPurchaseRepo assetPurchaseRepo;

    @Autowired
    CryptoRepo cryptoRepo;

    public AssetPurchase getAssetPurchase(PurchaseInfo purchaseDTO) {
        AssetPK pk = new AssetPK(loginService.getUserID(), purchaseDTO.getCoinId(), purchaseDTO.getCoinMarket());
        Optional<AssetPurchase> reserves = assetPurchaseRepo.findById(pk);

        AssetPurchase target;

        if (reserves.isEmpty())
            target = new AssetPurchase(loginService.getUserID(), purchaseDTO);
        else {
            target = reserves.get();

            target.addPrice(purchaseDTO.getBuyPrice());
            target.addCount(purchaseDTO.getBuyCount());
        }

        return target;
    }

//    public List<AssetPurchase> getAssetList() {
//        List<AssetPurchase> list = assetRepo.findAllByAccountID(loginService.getUserID());
//
//        return list;
//    }

}
