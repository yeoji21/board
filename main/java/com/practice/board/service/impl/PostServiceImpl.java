package com.practice.board.service.impl;

import com.practice.board.domain.post.Post;
import com.practice.board.domain.post.form.PostEditForm;
import com.practice.board.mapper.PostMapper;
import com.practice.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    @Override
    public void save(Post post) {
        postMapper.savePost(post);
    }

    @Override
    public List<Post> allPosts() {
        return postMapper.postList();
    }

    @Override
    public Post get(Long id) {
        return postMapper.getPost(id);
    }

    @Override
    public void delete(Long id) {
        postMapper.deletePost(id);
    }

    @Override
    public void update(Long id, PostEditForm post) {
        postMapper.updatePost(id,post);
    }

    @Override
    public List<Post> getFivePosts(int startPage) {
        int start = (startPage - 1)*5;
        return postMapper.postPage(start);
    }

    @Override
    public int pages() {
        int count = postMapper.postCount();
        if (count % 5 == 0) {
            return count/5;
        }
        return count/5+1;
    }

}
