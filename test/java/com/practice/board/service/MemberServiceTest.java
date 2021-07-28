package com.practice.board.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
    void deletePost() {

    }
}