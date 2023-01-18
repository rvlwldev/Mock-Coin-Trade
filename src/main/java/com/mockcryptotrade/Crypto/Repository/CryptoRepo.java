package com.mockcryptotrade.Crypto.Repository;

import com.mockcryptotrade.Crypto.Entity.Crypto;
import com.mockcryptotrade.Crypto.Entity.PK.CryptoPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepo extends JpaRepository<Crypto, CryptoPK> {
}
