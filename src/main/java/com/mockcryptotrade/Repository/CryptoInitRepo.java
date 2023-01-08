package com.mockcryptotrade.Repository;

import com.mockcryptotrade.Domain.Crypto.PK.CryptoID;
import com.mockcryptotrade.Domain.Crypto.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoInitRepo extends JpaRepository<Crypto, CryptoID> {
    List<Crypto> findAllByUseYn(int useYn);
}
