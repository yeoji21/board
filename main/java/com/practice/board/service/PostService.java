package com.practice.board.service;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.post.Post;
import com.practice.board.mapper.MemberMapper;
import com.practice.board.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final MemberMapper memberMapper;
    private final PostMapper postMapper;

    public void setName(Post post, Long id) {
        post.setName(memberMapper.findById(id).getName());
    }

    public List<Post> getFivePost(int page) {
        int start = (page - 1)*5;
        return postMapper.postPage(start, start + 5);
    }

    public int totalPages() {
        int count = postMapper.postCount();
        if (count % 5 == 0) {
            return count/5;
        }
        return count/5+1;
    }

}
