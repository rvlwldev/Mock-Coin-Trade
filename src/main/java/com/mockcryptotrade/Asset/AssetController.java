package com.mockcryptotrade.Asset;

import com.mockcryptotrade.Asset.Entity.AssetPurchase;
import com.mockcryptotrade.Asset.DTO.PurchaseInfo;
import com.mockcryptotrade.Asset.Repository.AssetPurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@RestController
public class AssetController {

    @Autowired
    AssetService assetService;

    @Autowired
    AssetPurchaseRepo assetPurchaseRepo;

    @GetMapping("/myPage")
    public ModelAndView showMyPage(Model model) {
        ModelAndView view = new ModelAndView("myPage/portfolio.html");

        return view;
    }


    @Transactional
    @PostMapping("/user/buy")
    public String buyCrypto(Model model, PurchaseInfo purchaseDTO) {
        AssetPurchase target = assetService.getAssetPurchase(purchaseDTO);

        System.out.println();
        System.out.println(target);
        System.out.println();


        assetPurchaseRepo.save(target);
        return "success";
    }

}
