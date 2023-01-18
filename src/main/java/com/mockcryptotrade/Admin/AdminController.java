package com.mockcryptotrade.Admin;

import com.mockcryptotrade.ExternalAPI.ApiService;
import com.mockcryptotrade.Crypto.DTO.CryptoInit;
import com.mockcryptotrade.Crypto.Entity.Crypto;
import com.mockcryptotrade.Crypto.Repository.CryptoInitRepo;
import com.mockcryptotrade.Crypto.Service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ApiService apiService;

    @Autowired
    CryptoService cryptoService;

    @Autowired
    CryptoInitRepo cryptoInfoRepo;

    @PostMapping("/initCryptoList")
    public String initCrypto() {
        try {
            List<CryptoInit> cryptoInitList = apiService.getCryptoInitList();
            List<Crypto> cryptoList = cryptoService.convertInitCryptoToEntity(cryptoInitList);

            cryptoInfoRepo.saveAll(cryptoList);
        } catch (Exception e) {
            return "실패 ㅠㅠ";
        }
        return "성공!";
    }


}
