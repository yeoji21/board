package com.practice.board.service;

import com.practice.board.domain.post.Post;
import com.practice.board.domain.post.form.PostEditForm;
import java.util.List;

public interface PostService {
    void save(Post post);
    List<Post> allPosts();
    Post get(Long id);
    void delete(Long id);
    void update(Long id, PostEditForm post);
    List<Post> getFivePosts(int startPage);
    int pages();
}
