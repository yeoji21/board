package com.practice.board.controller;


import com.practice.board.domain.comment.Comment;
import com.practice.board.domain.comment.form.CommentEditForm;
import com.practice.board.mapper.CommentMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/comment")
@Api(tags = "댓글 API")
@RequiredArgsConstructor
public class CommentController {
    private final CommentMapper commentMapper;

    @ApiOperation(value = "댓글 달기", notes = "게시글에 댓글 달기")
    @PostMapping("/addComment")
    public String addComment(@RequestParam("postId") Long postId, @RequestParam("memberId") Long memberId,
                             @RequestParam("comment") String comment, RedirectAttributes redirectAttributes) {
        Comment newComment = new Comment();
        newComment.setPostId(postId);
        newComment.setMemberId(memberId);
        newComment.setComment(comment);
        commentMapper.saveComment(newComment);

        redirectAttributes.addAttribute("id", newComment.getPostId());
        return "redirect:/post/{id}";
    }

    @PostMapping("/delete")
    public String deleteComment(@RequestParam("id") Long id, @RequestParam("postId") Long postId, RedirectAttributes redirectAttributes) {
        commentMapper.deleteComment(id);
        redirectAttributes.addAttribute("id", postId);
        return "redirect:/post/{id}";
    }

    @PostMapping("/update")
    @ResponseBody
    public Comment updateComment(@RequestParam("cid")Long cid, @RequestParam("content")String content){
//        log.warn("date = {}", date);
        CommentEditForm commentEditForm = new CommentEditForm();
        commentEditForm.setCid(cid);
        commentEditForm.setComment(content);
        commentEditForm.setDate(new Date());
        commentMapper.updateCommentAndDate(commentEditForm);
        Comment comment = commentMapper.getCommentById(commentEditForm.getCid());
        log.warn("comment = {}", comment.toString());
//        return content;
        return comment;
    }
}
