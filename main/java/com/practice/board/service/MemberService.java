package com.practice.board.service;

import com.practice.board.domain.member.Member;
import com.practice.board.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberMapper memberMapper;

    public boolean loginCheck(String loginId, String password) {
        return memberPasswordCheck(memberMapper.findByLoginID(loginId), password);
    }

    public boolean dupIdCheck(String loginId) {
        return memberIsNotNull(memberMapper.findByLoginID(loginId));
    }

    public boolean dupNameCheck(String name) {
        return memberIsNotNull(memberMapper.findByName(name));
    }

    private boolean memberIsNotNull(Member member) {
        return member != null;
    }

    private boolean memberPasswordCheck(Member member, String password) {
        return member != null && member.getPassword().equals(password);
    }
}
