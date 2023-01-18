package com.mockcryptotrade.Asset.Repository;

import com.mockcryptotrade.Asset.Entity.PK.AssetPK;
import com.mockcryptotrade.Asset.Entity.AssetSelect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetSelectRepo extends JpaRepository<AssetSelect, AssetPK> {
}
