package com.mockcryptotrade.Asset;

import com.mockcryptotrade.Account.Entity.AssetPurchase;
import com.mockcryptotrade.Account.Entity.PK.AssetPurchasePK;
import com.mockcryptotrade.Asset.DTO.PurchaseInfo;
import com.mockcryptotrade.Asset.Repository.AssetRepo;
import com.mockcryptotrade.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    LoginService loginService;

    @Autowired
    AssetRepo assetRepo;

    public AssetPurchase getAssetPurchase(PurchaseInfo purchaseDTO) {
        AssetPurchasePK pk = new AssetPurchasePK(loginService.getUserID(), purchaseDTO.getCoinId(), purchaseDTO.getCoinMarket());
        Optional<AssetPurchase> reserves = assetRepo.findById(pk);

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

}
