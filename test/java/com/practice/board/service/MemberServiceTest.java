package com.practice.board.service;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.form.MemberMyPageForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MemberServiceTest {

    @Autowired
    private MemberService memberService;


    @Test
    void passwordCheck() {
        boolean check = memberService.loginCheck("jiwon", "1234!");
        boolean expected = true;
        assertThat(check).isEqualTo(expected);
    }

    @Test
    void dupIdCheck() {
        boolean check = memberService.dupIdCheck("jiwon123");
        boolean expected = false;
        assertThat(check).isEqualTo(expected);
    }

    @Test
    void dupCheck() {
        String name = "silva21!!";
        String id = "jiwon";
        boolean check = memberService.dupNameCheck(name) || memberService.dupIdCheck(id);
        assertThat(check).isEqualTo(true);
    }

    @Test
    void passwordUpdate() {
//        MemberMyPageForm tester = new MemberMyPageForm("tester", "1234", "");
        MemberMyPageForm tester = new MemberMyPageForm();
        Member member = new Member("id", "pppp", "name", "dede");
        tester.setName("tester");
//        tester.setPassword("1234");
        log.info("tester.password = {}", tester.getPassword());
        String passwordCheck = "1234";
        if (tester.getPassword() != null && tester.getPassword().equals(passwordCheck)) {
            member.setPassword(tester.getPassword());
        }
//        assertThat(member.getPassword()).isEqualTo(tester.getPassword());
        assertThat(member.getPassword()).isNotEqualTo(passwordCheck);
    }
}