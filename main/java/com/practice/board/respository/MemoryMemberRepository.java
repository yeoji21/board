package com.practice.board.respository;

import com.practice.board.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();
    static long memberId = 0L;

    @Override
    public Member saveMember(Member member) {
        member.setId(++memberId);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Member updateMember(Long id, Member member) {
        Member findMember = findById(id);
        findMember.setDescription(member.getDescription());
        findMember.setName(member.getName());
        findMember.setPassword(member.getPassword());
        return findMember;
    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

}
