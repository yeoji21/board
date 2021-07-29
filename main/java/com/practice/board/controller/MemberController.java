package com.practice.board.controller;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.SessionConst;
import com.practice.board.domain.member.form.MemberMyPageForm;
import com.practice.board.domain.member.form.MemberSaveForm;
import com.practice.board.mapper.MemberMapper;
import com.practice.board.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags="사용자 컨트롤러")
public class MemberController {

    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @GetMapping("/add")
    @ApiOperation(value="회원가입 화면으로 이동", notes="회원가입 화면으로 이동")
    public String addMemberForm(@ModelAttribute("member")MemberSaveForm member) {
        return "member/addForm";
    }

    @PostMapping("/add")
    @ApiOperation(value="회원가입 처리", notes="사용자의 입력을 검사하고 회원가입 정보를 db에 저장")
    public String addMember(@Validated @ModelAttribute("member") MemberSaveForm memberSaveForm, BindingResult bindingResult) {
        if (bindingErrorCheck(memberSaveForm, memberService, bindingResult)) {
            log.warn("errors = {}", bindingResult);
            return "member/addForm";
        }
        memberMapper.saveMember(memberSaveFormToMember(memberSaveForm));
        return "redirect:/";
    }

    @GetMapping("/myPage")
    @ApiOperation(value="마이페이지로 이동", notes="마이 페이지를 보여줌")
    public String memberMyPage(@ModelAttribute("member") MemberMyPageForm memberMyPageForm, HttpServletRequest request,
                               Model model){
        Member findMember = getMemberFromSession(request);
        if (findMember == null) return "login/loginForm";
        model.addAttribute("member", findMember);
        return "member/myPage";
    }

    @PostMapping("/myPage")
    @ApiOperation(value="마이 페이지 수정", notes="마이 페이지 정보에대해 수정한 것을 적용")
    public String myPageEdit(@Validated @ModelAttribute("member") MemberMyPageForm memberMyPageForm,
                             BindingResult bindingResult, HttpServletRequest request) {
        if (myPageEditErrorCheck(bindingResult, memberService, memberMyPageForm)) {
            return "member/myPage";
        }
        Member findMember = getMemberFromSession(request);
        if(findMember == null) return "login/loginForm";

        memberUpdateNameAndDescription(memberMyPageForm, findMember);
        memberMapper.updateMember(findMember.getId(),findMember);
        //비밀번호 변경은 보류
        return "redirect:/members/myPage";
    }

    private void memberUpdateNameAndDescription(MemberMyPageForm memberMyPageForm, Member findMember) {
        findMember.setName(memberMyPageForm.getName());
        findMember.setDescription(memberMyPageForm.getDescription());
    }

    private boolean myPageEditErrorCheck(BindingResult bindingResult, MemberService memberService, MemberMyPageForm memberMyPageForm) {
        boolean nameCheck = memberService.dupNameCheck(memberMyPageForm.getName());
        if (nameCheck) {
            bindingResult.rejectValue("name", "duplicateName", "중복 닉네임입니다.");
        }
        return nameCheck || bindingResult.hasErrors();
    }

    private Member memberSaveFormToMember(MemberSaveForm memberSaveForm) {
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

    private Member getMemberFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
