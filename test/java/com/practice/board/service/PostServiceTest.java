package com.practice.board.service;

import com.practice.board.domain.comment.Comment;
import com.practice.board.domain.post.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class PostServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    @Test
    void getCommentsByPostId() {
        List<Post> posts = postService.getFivePosts(1);

        posts.forEach(
                post -> post.setComments(commentService.getNumByPostId(post.getId()))
        );
//        for (Post post : posts) {
//            int commentsNum = commentService.getNumByPostId(post.getId());
//            post.setComments(commentsNum);
//        }
        for (Post post : posts) {
            log.warn("comments = {}", post.getComments());
        }

    }

}