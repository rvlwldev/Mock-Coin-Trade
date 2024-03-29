package com.mockcryptotrade.Crypto.Repository;

import com.mockcryptotrade.Crypto.Entity.PK.CryptoPK;
import com.mockcryptotrade.Crypto.Entity.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoInitRepo extends JpaRepository<Crypto, CryptoPK> {
    List<Crypto> findAllByUseYn(int useYn);
}
