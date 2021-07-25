package com.practice.board.respository;

import com.practice.board.domain.member.Member;

import java.util.List;

public interface MemberRepository {
    //save
    int saveMember(Member member);

    //update
    Member updateMember(Long id, Member member);

    Member findById(Long id);

    List<Member> findAll();
}
