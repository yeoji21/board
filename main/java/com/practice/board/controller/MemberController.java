package com.practice.board.controller;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.SessionConst;
import com.practice.board.domain.member.form.MemberMyPageForm;
import com.practice.board.domain.member.form.MemberSaveForm;
import com.practice.board.domain.member.form.MemberUpdateForm;
import com.practice.board.mapper.MemberMapper;
import com.practice.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("member")MemberSaveForm member) {
        return "member/addForm";
    }

    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("member") MemberSaveForm memberSaveForm, BindingResult bindingResult) {
        if (bindingErrorCheck(memberSaveForm, memberService, bindingResult)) {
            log.warn("errors = {}", bindingResult);
            return "member/addForm";
        }
        memberMapper.saveMember(MemberSaveFormToMember(memberSaveForm));
        return "redirect:/";
    }

    @GetMapping("/myPage")
    public String memberMyPage(@ModelAttribute("member") MemberMyPageForm memberMyPageForm, HttpServletRequest request,
                               Model model){
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "login/loginForm";
        }
        Member findMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("member", findMember);
        return "member/myPage";
    }

    @PostMapping("/myPage")
    public String myPageEdit(@Validated @ModelAttribute("member") MemberMyPageForm memberMyPageForm,
                             BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "member/myPage";
        }

        if (memberService.dupNameCheck(memberMyPageForm.getName())) {
            bindingResult.rejectValue("name", "duplicateName", "중복 닉네임입니다.");
            return "member/myPage";
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "login/loginForm";
        }
        Member findMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        findMember.setName(memberMyPageForm.getName());
        findMember.setDescription(memberMyPageForm.getDescription());
        log.warn("findMember = {}", findMember.toString());
        memberMapper.updateMember(findMember.getId(),findMember);
        //비밀번호 변경은 보류
        return "redirect:/members/myPage";
    }


    private Member MemberEditFormToMember(Long id, MemberUpdateForm updateForm) {
        Member member = memberMapper.findById(id);
        member.setPassword(updateForm.getPassword());
        member.setName(updateForm.getName());
        member.setDescription(updateForm.getDescription());
        return member;
    }

    private Member MemberSaveFormToMember(MemberSaveForm memberSaveForm) {
        Member member = new Member();
        member.setLoginId(memberSaveForm.getLoginId());
        member.setDescription(memberSaveForm.getDescription());
        member.setPassword(memberSaveForm.getPassword());
        member.setName(memberSaveForm.getName());
        return member;
    }

    private boolean bindingErrorCheck(MemberSaveForm memberSaveForm, MemberService memberService, BindingResult bindingResult) {
        boolean nameCheck = memberService.dupNameCheck(memberSaveForm.getName());
        if(nameCheck){
            bindingResult.rejectValue("name", "duplicateName", "중복 닉네임입니다.");
        }

        boolean idCheck = memberService.dupIdCheck(memberSaveForm.getLoginId());
        if (idCheck) {
            bindingResult.rejectValue("loginId", "duplicatedLoginId", "중복 아이디입니다.");
        }

        return nameCheck || idCheck || bindingResult.hasErrors();
    }
}
