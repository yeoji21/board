package com.practice.board.controller;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.form.LoginForm;
import com.practice.board.domain.member.form.SessionConst;
import com.practice.board.mapper.MemberMapper;
import com.practice.board.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final MemberMapper memberMapper;
    private final LoginService loginService;

    @GetMapping
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request, @RequestParam(defaultValue = "/")String redirectURL) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        if (!loginService.loginCheck(loginForm.getLoginId(), loginForm.getPassword())){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";

        }
        Member findMember = memberMapper.findByLoginID(loginForm.getLoginId());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, findMember);
        return "redirect:"+redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
