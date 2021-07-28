package com.practice.board.controller;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.form.LoginForm;
import com.practice.board.domain.member.form.SessionConst;
import com.practice.board.mapper.MemberMapper;
import com.practice.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @GetMapping
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request, @RequestParam(defaultValue = "/")String redirectURL) {
        if (loginErrorCheck(loginForm, memberService, bindingResult)) {
            log.warn("errors = {}", bindingResult);
            return "login/loginForm";
        }
        createLoginSession(request, memberMapper.findByLoginID(loginForm.getLoginId()));
        return "redirect:"+redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        invalidateSession(request);
        return "redirect:/";
    }

    private void createLoginSession(HttpServletRequest request, Member findMember) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, findMember);
    }

    private boolean loginErrorCheck(LoginForm loginForm, MemberService memberService, BindingResult bindingResult) {
        boolean check = memberService.loginCheck(loginForm.getLoginId(), loginForm.getPassword());
        if (!check) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
        }
        return !check || bindingResult.hasErrors();
    }

    private void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
