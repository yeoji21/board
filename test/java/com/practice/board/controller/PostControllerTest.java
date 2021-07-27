package com.practice.board.controller;

import com.practice.board.domain.post.Post;
import com.practice.board.domain.post.form.PostSaveForm;
import com.practice.board.mapper.PostMapper;
import com.practice.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PostControllerTest {
    //    private final PostMapper postMapper;
    @Autowired
    private PostMapper postMapper;

    @Test
    void test() {
        Post post = postMapper.getPost(5L);
        log.warn("post={}", post.toString());
    }

    @Test
    void updateTest() {
        PostSaveForm postSaveForm = new PostSaveForm("update Test", "ddddd");
        postMapper.updatePost(5L, postSaveForm);
        log.warn("===update===");
        Post post = postMapper.getPost(5L);
        log.warn("post={}", post.toString());
    }
}