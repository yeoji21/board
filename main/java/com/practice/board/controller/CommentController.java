package com.practice.board.controller;


import com.practice.board.domain.comment.Comment;
import com.practice.board.mapper.CommentMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String deleteComment(@RequestParam("id") Long id, @RequestParam("postId")Long postId, RedirectAttributes redirectAttributes) {
        commentMapper.deleteComment(id);
        redirectAttributes.addAttribute("id",postId);
        return "redirect:/post/{id}";
    }
}
