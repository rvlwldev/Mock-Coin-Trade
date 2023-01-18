package com.mockcryptotrade.Account.Repository;

import com.mockcryptotrade.Account.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, String> {
    boolean existsByNickname(String s);
}
