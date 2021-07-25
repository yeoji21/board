package com.practice.board.controller;

import com.practice.board.domain.Member;
import com.practice.board.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberRepository repository;

    @GetMapping("/add")
    public String addMemberForm(@ModelAttribute("member") Member member) {
        return "member/addForm";
    }

    @PostMapping("/add")
    public String addMember(@ModelAttribute("member") Member member, @RequestParam("loginId") String id) {
        log.warn("id = {}", id);
        Member saveMember = repository.saveMember(member);
        log.warn(saveMember.toString());
        return "redirect:/";
    }
}
