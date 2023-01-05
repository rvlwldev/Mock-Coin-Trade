package com.mockcryptotrade.Repository.Interface;

import com.mockcryptotrade.Domain.Crypto.CryptoID;
import com.mockcryptotrade.Domain.Crypto.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoInitRepo extends JpaRepository<Crypto, CryptoID> {

    public List<Crypto> findAllByUseYn(int useYn);
}
