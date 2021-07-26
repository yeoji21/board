package com.practice.board.controller;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.form.MemberSaveForm;
import com.practice.board.domain.member.form.MemberUpdateForm;
import com.practice.board.mapper.MemberMapper;
import com.practice.board.respository.MemberRepository;
import com.practice.board.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberMapper memberMapper;
    //    private final MemberRepository repository;
    private final LoginService loginService;

    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("member")MemberSaveForm member) {
        return "member/addForm";
    }

    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("member") MemberSaveForm memberSaveForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("errors = {}", bindingResult);
            return "member/addForm";
        }

        if(loginService.dupIdCheck(memberSaveForm.getLoginId())){
//            bindingResult.reject("duplicatedLoginId","중복 아이디입니다.");
            bindingResult.rejectValue("loginId", "duplicatedLoginId", "중복 아이디입니다.");
            return "member/addForm";

        }

        if (loginService.dupNameCheck(memberSaveForm.getName())) {
            bindingResult.rejectValue("name", "duplicateName", "중복 닉네임입니다.");
            return "member/addForm";
        }

        Member member = new Member();
        member.setLoginId(memberSaveForm.getLoginId());
        member.setDescription(memberSaveForm.getDescription());
        member.setPassword(memberSaveForm.getPassword());
        member.setName(memberSaveForm.getName());
        memberMapper.saveMember(member);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Member findMember(@PathVariable Long id) {
        Member member = memberMapper.findById(id);
        log.warn(member.toString());
        return member;
    }

    @GetMapping
    @ResponseBody
    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public Member updateMember(@PathVariable Long id, @ModelAttribute("model")MemberUpdateForm updateForm) {
        Member member = memberMapper.findById(id);
        member.setPassword(updateForm.getPassword());
        member.setName(updateForm.getName());
        member.setDescription(updateForm.getDescription());
        memberMapper.updateMember(id, member);
        return member;
    }
}
