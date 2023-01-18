package com.mockcryptotrade.Repository.User;

import com.mockcryptotrade.Domain.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, String> {
    boolean existsByNickname(String s);
}
