package com.practice.board.service;

import com.practice.board.domain.member.Member;
import com.practice.board.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final MemberMapper memberMapper;

    public boolean loginCheck(String loginId, String password) {
        Member member = memberMapper.findByLoginID(loginId);
        if (member != null && member.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public boolean dupIdCheck(String loginId) {
        Member member = memberMapper.findByLoginID(loginId);
        if(member != null){
            log.warn("member = {}", member);
            return true;
        }
        return false;
    }

    public boolean dupNameCheck(String name) {
        Member member = memberMapper.findByName(name);
        if (member != null) {
            log.warn("member = {}", member);
            return true;
        }
        return false;
    }
}
