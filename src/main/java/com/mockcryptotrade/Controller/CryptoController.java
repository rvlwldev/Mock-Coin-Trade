package com.mockcryptotrade.Controller;

import com.mockcryptotrade.Domain.Crypto.Crypto;
import com.mockcryptotrade.Domain.Crypto.CryptoDetail;
import com.mockcryptotrade.Repository.Interface.CryptoInitRepo;
import com.mockcryptotrade.Service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CryptoController {

    @Autowired
    CryptoService service;

    @Autowired
    CryptoInitRepo cryptoRepo;

    @GetMapping("/")
    public ModelAndView getCryptoDetailList(Model model) throws IOException {
        ModelAndView view = new ModelAndView("index.html");

        List<Crypto> cryptos = cryptoRepo.findAllByUseYn(1);
//                .subList(0, 10);

        List<CryptoDetail> list = new ArrayList<>();

        for (Crypto c : cryptos) {
            CryptoDetail detail = service.getDetails(c.getCryptoId(), c.getCryptoMarket(), c.getFullNameKO());

            if (detail != null) {
                list.add(detail);
            }
        }

        list.sort((o1, o2) -> o2.getAccTradePrice24h() - o1.getAccTradePrice24h());

        model.addAttribute("list", list);

        return view;
    }


}
