package com.practice.board.controller;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.form.LoginForm;
import com.practice.board.domain.member.SessionConst;
import com.practice.board.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags="로그인 API")
public class LoginController {
    private final MemberService memberService;

    @GetMapping
    @ApiOperation(value="로그인 화면으로 이동", notes="로그인 화면으로 이동")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping
    @ApiOperation(value="로그인 처리", notes="사용자의 입력을 받아 로그인 처리")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request, @RequestParam(defaultValue = "/")String redirectURL) {
        if (loginErrorCheck(loginForm, memberService, bindingResult)) {
            log.warn("errors = {}", bindingResult);
            return "login/loginForm";
        }
        createLoginSession(request, memberService.findByLoginID(loginForm.getLoginId()));
        return "redirect:"+redirectURL;
    }

    @GetMapping("/logout")
    @ApiOperation(value="로그아웃", notes="로그아웃")
    public String logout(HttpServletRequest request) {
        invalidateSession(request);
        return "redirect:/";
    }

    private void createLoginSession(HttpServletRequest request, Member findMember) {
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, findMember);
    }

    private boolean loginErrorCheck(LoginForm loginForm, MemberService memberService, BindingResult bindingResult) {
        boolean check = memberService.passwordCheck(loginForm.getLoginId(), loginForm.getPassword());
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
