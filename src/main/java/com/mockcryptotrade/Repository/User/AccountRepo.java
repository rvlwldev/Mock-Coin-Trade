package com.mockcryptotrade.Repository.User;

import com.mockcryptotrade.Domain.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, String> {

}
