package com.practice.board.respository;

import com.practice.board.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MemoryMemberRepositoryTest {
    MemberRepository memberRepository = new MemoryMemberRepository();

    @Test
    void findById() {
        Member member1 = new Member("test1", "1234", "tester", "hi");
        Member member2 = new Member("test2", "1234", "tester", "hi");

        memberRepository.saveMember(member1);
        memberRepository.saveMember(member2);

        Member findMember = memberRepository.findById(member1.getId());
        log.warn(findMember.toString());
        assertThat(findMember).isEqualTo(member1);
    }

    @Test
    void findAll() {
        Member member1 = new Member("test1", "1234", "tester", "hi");
        Member member2 = new Member("test2", "1234", "tester", "hi");
        memberRepository.saveMember(member1);
        memberRepository.saveMember(member2);
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(2);
    }
}