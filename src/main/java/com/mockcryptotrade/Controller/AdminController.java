package com.mockcryptotrade.Controller;

import com.mockcryptotrade.Common.ApiService;
import com.mockcryptotrade.Domain.Crypto.CryptoInfo;
import com.mockcryptotrade.Repository.Interface.CryptoInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ApiService apiService;

    @Autowired
    CryptoInfoRepo cryptoInfoRepo;

    @PostMapping("/initCryptoList")
    public String initCrypto(@RequestParam Map<String, Object> param) throws IOException {
        try {
            List<CryptoInfo> CryptoInfoList = apiService.initializeCryptoNames();
            cryptoInfoRepo.saveAll(CryptoInfoList);
        } catch (Exception e) {
            return "실패 ㅠㅠ";
        }
        return "성공!";
    }


}
