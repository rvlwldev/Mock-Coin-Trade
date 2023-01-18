package com.mockcryptotrade.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/myPage")
public class UserController {

    @GetMapping("")
    public ModelAndView showMyPage(Model model) {
        ModelAndView view = new ModelAndView("myPage/portfolio.html");
        return view;
    }

}
