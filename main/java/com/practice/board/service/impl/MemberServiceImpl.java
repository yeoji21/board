package com.practice.board.service.impl;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.post.Post;
import com.practice.board.mapper.MemberMapper;
import com.practice.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public void save(Member member) {
        memberMapper.saveMember(member);
    }

    @Override
    public void update(Long id, Member member) {
        memberMapper.updateMember(id,member);
    }

    @Override
    public Member findById(Long id) {
        return memberMapper.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    @Override
    public Member findByLoginID(String loginId) {
        return memberMapper.findByLoginID(loginId);
    }

    @Override
    public Member findByName(String name) {
        return memberMapper.findByName(name);
    }

    @Override
    public boolean dupIdCheck(String loginId) {
        return memberMapper.findByLoginID(loginId)!=null;
    }

    @Override
    public boolean dupNameCheck(String name) {
        return memberMapper.findByName(name)!=null;
    }

    @Override
    public boolean passwordCheck(String loginId, String password) {
        return memberMapper.findByLoginID(loginId).getPassword().equals(password);
    }

    public void setName(Post post, Long id) {
        post.setName(memberMapper.findById(id).getName());
    }
}
