package com.mockcryptotrade.Asset.Repository;

import com.mockcryptotrade.Account.Entity.AssetPurchase;
import com.mockcryptotrade.Account.Entity.PK.AssetPurchasePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepo extends JpaRepository<AssetPurchase, AssetPurchasePK> {
}
