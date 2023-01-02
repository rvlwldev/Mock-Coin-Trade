package com.mockcryptotrade.Repository.Interface;

import com.mockcryptotrade.Domain.Crypto.CryptoInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoInfoRepo extends JpaRepository<CryptoInfo, String> {
//    void reset();
}
