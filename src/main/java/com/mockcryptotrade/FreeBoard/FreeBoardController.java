package com.mockcryptotrade.FreeBoard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class FreeBoardController {

    @GetMapping("/freeboard")
    public ModelAndView boardList(){
        ModelAndView view = new ModelAndView("freeboard/boardList.html");
        return view;
    }

}
