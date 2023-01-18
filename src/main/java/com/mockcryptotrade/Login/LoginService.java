package com.mockcryptotrade.Login;

import com.mockcryptotrade.Login.LoginUser;
import com.mockcryptotrade.Account.Entity.Account;
import com.mockcryptotrade.Account.Repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        Account account = accountRepo.findById(userID).get();

        if (account == null) {
            throw new UsernameNotFoundException(userID);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(account.getRoleCode())));

        LoginUser user = new LoginUser(account.getID(), account.getPassword(), authorities);
        user.setNickname(account.getNickname());

        return user;

//        return new User(account.getID(), account.getPassword(), authorities);
    }

    public void createNewAccount(Account account) {
        String encrypt = passwordEncoder.encode(account.getPassword());

        account.setRoleCode(1);
        account.setPassword(encrypt);
        account.setRegistrationDate(LocalDateTime.now());

        accountRepo.save(account);
    }

    public String getUserID() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;

       return userDetails.getUsername();
    }


}
