package com.practice.board;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.form.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false)Member member, Model model) {
        if(member == null){
            return "home";
        }
        model.addAttribute("member", member);
        return "loginHome";
    }
}
