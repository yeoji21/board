package com.practice.board.service;

import com.practice.board.domain.comment.Comment;
import com.practice.board.domain.comment.form.CommentEditForm;
import com.practice.board.domain.comment.form.CommentSaveForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentService {
    void save(Comment comment);
    List<Comment> getByPostId(Long postId);
    List<Comment> getByMemberAndPostId(Long postId,Long memberId);
    Comment getById(Long id);
    void delete(Long id);
    void deleteByPostId(@Param("postId") Long postId);
    void update(CommentEditForm comment);

}
