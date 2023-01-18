package com.mockcryptotrade.Asset.Repository;

import com.mockcryptotrade.Asset.Entity.AssetPurchase;
import com.mockcryptotrade.Asset.Entity.PK.AssetPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetPurchaseRepo extends JpaRepository<AssetPurchase, AssetPK> {
    List<AssetPurchase> findAllByAccountID(String accountID);
}
