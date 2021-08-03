package com.practice.board.controller;


import com.practice.board.domain.comment.Comment;
import com.practice.board.domain.comment.form.CommentEditForm;
import com.practice.board.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/comments")
@Api(tags = "댓글 API")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 달기", notes = "게시글에 댓글 달기")
    @PostMapping("/{postId}")
    public String addComment(@PathVariable Long postId, @RequestParam("memberId") Long memberId, @RequestParam("comment") String comment) {
        Comment newComment = getComment(postId, memberId, comment);
        commentService.save(newComment);
//        redirectAttributes.addAttribute("id", newComment.getPostId());
        return "redirect:/posts/{postId}";
    }

    @Transactional
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id, @RequestParam("postId") Long postId, RedirectAttributes redirectAttributes) {
        commentService.delete(id);
        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/posts/{postId}";
    }

    @Transactional
    @PutMapping("/{id}")
    @ResponseBody
    public Comment updateComment(@PathVariable Long id, @RequestParam("content")String content){
        CommentEditForm commentEditForm = getCommentEditForm(id, content);
        commentService.update(commentEditForm);
        Comment comment = commentService.getById(commentEditForm.getCid());
        log.warn("comment = {}", comment.toString());
        return comment;
    }

    private CommentEditForm getCommentEditForm(Long cid, String content) {
        CommentEditForm commentEditForm = new CommentEditForm();
        commentEditForm.setCid(cid);
        commentEditForm.setComment(content);
        commentEditForm.setDate(new Date());
        return commentEditForm;
    }

    private Comment getComment(Long postId, Long memberId, String comment) {
        Comment newComment = new Comment();
        newComment.setPostId(postId);
        newComment.setMemberId(memberId);
        newComment.setComment(comment);
        return newComment;
    }
}
