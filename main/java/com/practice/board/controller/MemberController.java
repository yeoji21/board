package com.practice.board.controller;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.form.MemberSaveForm;
import com.practice.board.mapper.MemberMapper;
import com.practice.board.respository.MemberRepository;
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

        Member member = new Member();
        member.setLoginId(memberSaveForm.getLoginId());
        member.setDescription(memberSaveForm.getDescription());
        member.setPassword(memberSaveForm.getPassword());
        member.setName(memberSaveForm.getName());
//        Member saveMember = repository.saveMember(member);
        memberMapper.saveMember(member);
        return "redirect:/";
    }
}
