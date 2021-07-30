package com.practice.board.controller;

import com.practice.board.domain.post.Post;
import com.practice.board.domain.post.form.PostSaveForm;
import com.practice.board.mapper.PostMapper;
import com.practice.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@WebMvcTest(PostController.class)
class PostControllerTest {
    //    private final PostMapper postMapper;
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test() {
        Post post = postMapper.getPost(5L);
        log.warn("post={}", post.toString());
    }

    @Test
    void pageCount() {
        int count = postMapper.postCount();
        assertThat(count).isEqualTo(7);
    }

    @Test
    @Transactional
    void deletePage() {
        int test = postMapper.deletePost(11L);
        assertThat(test).isEqualTo(1);
    }


    @Test
    void totalPages() {
//        int count = postMapper.postCount();
        int count = 5;
        if (count % 5 == 0) {
            count = count / 5;
        }
        else{
            count = count/5 +1;
        }
        assertThat(count).isEqualTo(1);
    }
}