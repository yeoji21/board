package com.practice.board.mapper;

import com.practice.board.domain.comment.Comment;
import com.practice.board.domain.comment.form.CommentSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Test
    @Transactional
    void insert() {
        Comment comment = new Comment();
        comment.setMemberId(6L);
        comment.setPostId(21L);
        comment.setComment("first comment");
        commentMapper.saveComment(comment);
        Long postId = comment.getPostId();
        assertThat(postId).isEqualTo(21L);
        List<Comment> commentList = commentMapper.getComment(21L);
        commentList.forEach(comment1-> log.warn("comment = {}", comment1));
    }

    @Test
    @Transactional
    void getId() {
        List<Comment> commentList = commentMapper.getCommentByMemberAndPost(21L, 6L);
        Optional<Comment> comment = commentList.stream().filter(Objects::nonNull).findAny();
        Long findId = comment.get().getId();
        assertThat(findId).isEqualTo(1L);
    }

    @Test
    @Transactional
    void setName() {
        List<Comment> commentList = commentMapper.getComment(21L);
        List<CommentSaveForm> namedCommentList = new ArrayList();
        for (Comment comment : commentList) {
            CommentSaveForm commentSaveForm = new CommentSaveForm();
            commentSaveForm.setName(memberMapper.findById(comment.getMemberId()).getName());
            commentSaveForm.setComment(comment.getComment());
            commentSaveForm.setDate(comment.getDate());
            namedCommentList.add(commentSaveForm);
        }

        log.warn("list = {}", namedCommentList);
        assertThat(namedCommentList).hasSize(4);
    }

    @Test
    @Transactional
    void deleteCommentByPostId() {
        commentMapper.deleteCommentByPostId(21L);
        List<Comment> comment = commentMapper.getComment(21L);
        assertThat(comment.size()).isEqualTo(0);
    }

}