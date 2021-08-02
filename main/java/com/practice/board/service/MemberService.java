package com.practice.board.service;

import com.practice.board.domain.member.Member;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberService {
    void save(Member member);
    void update(Long id, Member member);
    Member findById(Long id);
    List<Member> findAll();
    Member findByLoginID(String id);
    Member findByName(String name);
    boolean dupIdCheck(String loginId);
    public boolean dupNameCheck(String name);
    boolean passwordCheck(String loginId, String password);
}
