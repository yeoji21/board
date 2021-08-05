package com.practice.board.service.impl;

import com.practice.board.domain.comment.Comment;
import com.practice.board.domain.comment.form.CommentEditForm;
import com.practice.board.domain.comment.form.CommentSaveForm;
import com.practice.board.mapper.CommentMapper;
import com.practice.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public void save(Comment comment) {
        commentMapper.saveComment(comment);
    }

    @Override
    public List<Comment> getByPostId(Long postId) {
        return commentMapper.getComment(postId);
    }

    @Override
    public List<Comment> getByMemberAndPostId(Long postId, Long memberId) {
        return commentMapper.getCommentByMemberAndPost(postId,memberId);
    }

    @Override
    public Comment getById(Long id) {
        return commentMapper.getCommentById(id);
    }

    @Override
    public void delete(Long id) {
        commentMapper.deleteComment(id);
    }

    @Override
    public void deleteByPostId(Long postId) {
        commentMapper.deleteCommentByPostId(postId);
    }

    @Override
    public void update(CommentEditForm comment) {
        commentMapper.updateCommentAndDate(comment);
    }

    @Override
    public int getNumByPostId(Long postId) {
        return commentMapper.getNumByPostId(postId);
    }

}
