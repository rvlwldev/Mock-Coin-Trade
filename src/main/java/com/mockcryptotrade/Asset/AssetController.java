package com.mockcryptotrade.Asset;

import com.mockcryptotrade.Account.Entity.AssetPurchase;
import com.mockcryptotrade.Asset.DTO.PurchaseInfo;
import com.mockcryptotrade.Asset.Repository.AssetRepo;
import com.mockcryptotrade.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AssetController {

    @Autowired
    LoginService loginService;

    @Autowired
    AssetService assetService;

    @Autowired
    AssetRepo assetRepo;

    @GetMapping("/myPage")
    public ModelAndView showMyPage(Model model) {
        ModelAndView view = new ModelAndView("myPage/portfolio.html");

        // 보유코인 보여주기
//        model.addAttribute("userBuyList", )

        return view;
    }


    @PostMapping("/user/buy")
    public String buyCrypto(Model model, PurchaseInfo purchaseDTO) {
        AssetPurchase target = assetService.getAssetPurchase(purchaseDTO);
        assetRepo.save(target);
        return "success";
    }

}
