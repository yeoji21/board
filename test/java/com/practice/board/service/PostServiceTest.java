package com.practice.board.service;

import com.practice.board.domain.comment.Comment;
import com.practice.board.domain.post.Post;
import com.practice.board.mapper.CommentMapper;
import com.practice.board.service.impl.CommentServiceImpl;
import com.practice.board.service.impl.PostServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private CommentMapper commentMapper;

    //    @Autowired
    @InjectMocks
    private CommentServiceImpl commentService;
//    private CommentService commentService;


    @Autowired
    private PostService postService;


    @Test
    void createCommentServiceUsingMockito() {
//        commentService = new CommentServiceImpl(commentMapper);
        assertThat(commentService).isNotNull();

        Mockito.when(commentMapper.getComment(25L)).then( invocation -> {
            System.out.println("call getComment()");
            List<Comment> result = new ArrayList();
            result.add(new Comment(1232L, 16L, 25L, "hello", new Date()));
            return result;
        });

        List<Comment> comment = commentService.getByPostId(25L);
        log.warn("comment = {}", comment);
        assertThat(comment).isNotNull();
    }

    @Test
    void getCommentsByPostId() {
        List<Post> posts = postService.getFivePosts(1);

        posts.forEach(
                post -> post.setComments(commentService.getNumByPostId(post.getId()))
        );
        for (Post post : posts) {
            log.warn("comments = {}", post.getComments());
        }

    }

}