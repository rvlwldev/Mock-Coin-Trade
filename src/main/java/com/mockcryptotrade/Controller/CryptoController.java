package com.mockcryptotrade.Controller;

import com.mockcryptotrade.Common.ApiService;
import com.mockcryptotrade.Domain.Crypto.Crypto;
import com.mockcryptotrade.Domain.Crypto.CryptoDetail;
import com.mockcryptotrade.Repository.CryptoInitRepo;
import com.mockcryptotrade.Service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class CryptoController {

    @Autowired
    CryptoService cryptoService;

    @Autowired
    ApiService apiService;

    @Autowired
    CryptoInitRepo cryptoRepo;

    @GetMapping("/")
    public ModelAndView getCryptoDetailList(Model model) {
        ModelAndView view = new ModelAndView("index.html");
        model.addAttribute("list", getCryptoDetailList());
        return view;
    }

    @GetMapping("/refresh")
    public List<CryptoDetail> refreshCryptoDetailList() {
        return getCryptoDetailList();
    }

    private List<CryptoDetail> getCryptoDetailList () {
        List<Crypto> cryptos = cryptoRepo.findAllByUseYn(1);
        String param = cryptoService.getParamValueForCryptoDetails(cryptos);

        List<CryptoDetail> details = apiService.getCryptoDetailList(cryptos, param);

        details.sort((o1, o2) -> (int) (o2.getAccTradePrice24h() - o1.getAccTradePrice24h()));

        return details;
    }


}
