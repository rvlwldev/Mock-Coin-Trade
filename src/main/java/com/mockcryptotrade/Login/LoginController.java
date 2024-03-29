package com.mockcryptotrade.Login;

import com.mockcryptotrade.Account.Entity.Account;
import com.mockcryptotrade.Account.Repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "auth")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    AccountRepo accountRepo;

    private ResponseEntity goMain() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/register")
    public ResponseEntity createAccount(Account account) {
        loginService.createNewAccount(account);
        return goMain();
    }

    @PostMapping("/checkID")
    public boolean checkDupID(String id) {
        return accountRepo.existsById(id);
    }

    @PostMapping("/checkNickname")
    public boolean checkDupNickname(String nickname) {
        return accountRepo.existsByNickname(nickname);
    }

}
