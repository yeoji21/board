package com.practice.board.service;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.post.Post;
import com.practice.board.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final MemberMapper memberMapper;
    public void setName(Post post, Long id) {
        Member member = memberMapper.findById(id);
        post.setName(member.getName());
    }

}
