package com.mockcryptotrade.Asset.Repository;

import com.mockcryptotrade.Asset.Entity.AssetLog;
import com.mockcryptotrade.Asset.Entity.PK.AssetLogPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssetLogRepo extends JpaRepository<AssetLog, AssetLogPK> {
    @Modifying
    @Query("delete from AssetLog where accountID = :id")
    void deleteAssetLogsByAccountID(@Param("id") String accountID);
}
