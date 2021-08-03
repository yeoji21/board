package com.practice.board.service.impl;

import com.practice.board.domain.comment.Comment;
import com.practice.board.domain.comment.form.CommentSaveForm;
import com.practice.board.mapper.CommentMapper;
import com.practice.board.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LegacyCommentService {
    private final MemberMapper memberMapper;
    private final CommentMapper commentMapper;

    public List<CommentSaveForm> namedCommentList(Long postId) {
        List<Comment> commentList = commentMapper.getComment(postId);
        List<CommentSaveForm> namedCommentList = new ArrayList();
        for (Comment comment : commentList) {
            CommentSaveForm commentSaveForm = new CommentSaveForm();
            commentSaveForm.setId(comment.getId());
            commentSaveForm.setName(memberMapper.findById(comment.getMemberId()).getName());
            commentSaveForm.setComment(comment.getComment());
            commentSaveForm.setDate(comment.getDate());
            namedCommentList.add(commentSaveForm);
        }
        return namedCommentList;
    }

    public void deleteCommentInPost(Long postId) {
        List<Comment> commentList = commentMapper.getComment(postId);
        if(commentList.size()<0){
            return;
        }
        commentMapper.deleteCommentByPostId(postId);
    }

}
