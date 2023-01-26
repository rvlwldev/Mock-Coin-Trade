package com.mockcryptotrade.Asset;

import com.mockcryptotrade.Asset.DTO.AssetDetail;
import com.mockcryptotrade.Asset.DTO.PurchaseInfo;
import com.mockcryptotrade.Asset.DTO.SellInfo;
import com.mockcryptotrade.Asset.Entity.AssetLog;
import com.mockcryptotrade.Asset.Entity.AssetPurchase;
import com.mockcryptotrade.Asset.Entity.AssetSelect;
import com.mockcryptotrade.Asset.Repository.AssetLogRepo;
import com.mockcryptotrade.Asset.Repository.AssetPurchaseRepo;
import com.mockcryptotrade.Asset.Repository.AssetSelectRepo;
import com.mockcryptotrade.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class AssetController {

    @Autowired
    LoginService loginService;

    @Autowired
    AssetService assetService;

    @Autowired
    AssetPurchaseRepo assetPurchaseRepo;

    @Autowired
    AssetLogRepo assetLogRepo;

    @Autowired
    AssetSelectRepo assetSelectRepo;

    @GetMapping("/myPage")
    public ModelAndView showMyPage(Model model) {
        ModelAndView view = new ModelAndView("myPage/portfolio.html");
        view.addObject("statCoinsList", assetService.statCoin());
        view.addObject("statDaysList", assetService.statDay());
        view.addObject("statMonthsList", assetService.statMonth());

        List<AssetSelect> list = assetSelectRepo.findAllByAccountID(loginService.getUserID());

        List<AssetDetail> detailList = assetService.getUsersAssetDetails(list);
        if (detailList == null) {
            model.addAttribute("totalPercent", 0);
            model.addAttribute("totalMoney", 0);
            return view;
        }

        model.addAttribute("list", detailList);
        double totalPercent = assetService.getTotalRateOfReturn(detailList);
        double totalMoney = assetService.getTotalEarnMoney(detailList, totalPercent);

        model.addAttribute("totalPercent", totalPercent); // 총 수익률
        model.addAttribute("totalMoney", totalMoney); // 총 수익금

        return view;
    }

    @PostMapping("/myPage/refresh")
    public List<Object> synchronizeAsset() {
        List<AssetSelect> list = assetSelectRepo.findAllByAccountID(loginService.getUserID());
        List<AssetDetail> detailList = assetService.getUsersAssetDetails(list);

        double totalPercent = assetService.getTotalRateOfReturn(detailList);
        double totalMoney = assetService.getTotalEarnMoney(detailList, totalPercent);

        return List.of(detailList, totalPercent, totalMoney);
    }

    @Transactional
    @PostMapping("/user/buy")
    public String buyCrypto(Model model, PurchaseInfo purchaseDTO) {
        AssetPurchase target = assetService.getAssetPurchase(purchaseDTO);
        assetPurchaseRepo.save(target);
        return "success";
    }

    @Transactional
    @PostMapping("/user/sell")
    public String sellCrypto(Model model, SellInfo sellDTO) {
        AssetPurchase target = assetService.getAssetPurchase(sellDTO);

        if (target.getCryptoCount() > 0) {
            assetPurchaseRepo.save(target);
        } else {
            assetPurchaseRepo.delete(target);
        }

        assetLogRepo.save(new AssetLog(sellDTO, target));

        return "success";
    }

    @Transactional
    @PostMapping("/user/resetLog")
    public String resetLog(Model model) {
        assetLogRepo.deleteAssetLogsByAccountID(loginService.getUserID());
        return "success";
    }
}
