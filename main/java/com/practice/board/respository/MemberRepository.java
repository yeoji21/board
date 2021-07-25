package com.practice.board.respository;

import com.practice.board.domain.Member;

import java.util.List;

public interface MemberRepository {
    //save
    Member saveMember(Member member);

    //update
    Member updateMember(Long id, Member member);

    Member findById(Long id);

    List<Member> findAll();
}
