package com.practice.board.controller;

import com.practice.board.domain.comment.Comment;
import com.practice.board.domain.comment.form.CommentSaveForm;
import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.SessionConst;
import com.practice.board.domain.post.Post;
import com.practice.board.domain.post.form.PostEditForm;
import com.practice.board.domain.post.form.PostSaveForm;
import com.practice.board.service.CommentService;
import com.practice.board.service.MemberService;
import com.practice.board.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
@Api(tags = "게시글 API")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;

    @GetMapping
    @ApiOperation(value="게시판 화면 출력", notes="post의 id의 역순으로 게시물 5개씩 가져옴")
    public String pageList(@RequestParam(value = "page", defaultValue ="1") int page, Model model) {
        model.addAttribute("lists", postService.getFivePosts(page));
        model.addAttribute("pages", postService.pages());
        return "post/list";
    }

    @GetMapping("/add")
    @ApiOperation(value="게시물 추가 화면으로 이동", notes="글 쓰기 화면으로 이동")
    public String addPost(@ModelAttribute("post") PostSaveForm postSaveForm) {
        return "post/addForm";
    }

    @PostMapping("/add")
    @ApiOperation(value="게시물 저장", notes="글 쓰기를 화면에 입력한 정보를 검토하고 db에 저장함")
    public String savePost(@Validated @ModelAttribute("post") PostSaveForm postSaveForm, BindingResult bindingResult,
                           HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("errors={}", bindingResult);
            return "post/addForm";
        }
        Post post = postSaveFormToPost(postSaveForm, request);
        postService.save(post);
        redirectAttributes.addAttribute("id", post.getId());
        return "redirect:/post/{id}";
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "게시글 읽기", notes = "게시글 id를 통해 게시물 하나를 읽어옴")
    public String readPost(@PathVariable Long id, Model model) {
        List<CommentSaveForm> comments = namedCommentList(id);
        model.addAttribute("comments", comments);
        model.addAttribute("post", postService.get(id));
        return "post/post";
    }

    @PostMapping("/editForm")
    @ApiOperation(value="게시글 수정 화면으로 이동", notes="게시글 수정 화면으로 이동")
    public String editForm(@ModelAttribute("post") PostEditForm postEditForm, @RequestParam("id") Long id,Model model) {
        model.addAttribute("post", postService.get(id));
        return "post/editForm";
    }

    @PostMapping("/edit")
    @ApiOperation(value="게시글 수정 적용", notes="게시글 수정 화면에서 입력한 정보로 게시글을 수정")
    public String edit(@Validated @ModelAttribute("post") PostEditForm postEditForm, BindingResult bindingResult,
                        @RequestParam("id") Long id,HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("errors = {}", bindingResult);
            return "/post/editForm";
        }
        postEditFormSetProperties(postEditForm, request);
        log.warn("update post id = {}", id);
        postService.update(id, postEditForm);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/post/{id}";
    }

    @PostMapping("/delete")
    @ApiOperation(value="게시글 삭제", notes="게시글 삭제")
    public String deletePost(@RequestParam("id") Long id) {
        commentService.deleteByPostId(id);
        postService.delete(id);
        return "redirect:/post";
    }

    private Post postSaveFormToPost(PostSaveForm postSaveForm, HttpServletRequest request) {
        Post post = new Post();
        setPostMemberId(post, request.getSession(false));
        memberService.setName(post, post.getMemberId());
        post.setTitle(postSaveForm.getTitle());
        post.setContent(postSaveForm.getContent());
        return post;
    }

    private void setPostMemberId(Post post, HttpSession session) {
        if (session == null) {
            return;
        }
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        post.setMemberId(loginMember.getId());
    }

    private void postEditFormSetProperties(PostEditForm postEditForm, HttpServletRequest request) {
        postEditForm.setName(((Member) request.getSession(false).getAttribute(SessionConst.LOGIN_MEMBER)).getName());
        postEditForm.setPostDate(new Date());
    }

    private List<CommentSaveForm> namedCommentList(Long postId) {
        List<Comment> commentList = commentService.getByPostId(postId);
        List<CommentSaveForm> namedCommentList = new ArrayList();
        for (Comment comment : commentList) {
            CommentSaveForm commentSaveForm = new CommentSaveForm();
            commentSaveForm.setId(comment.getId());
            commentSaveForm.setName(memberService.findById(comment.getMemberId()).getName());
            commentSaveForm.setComment(comment.getComment());
            commentSaveForm.setDate(comment.getDate());
            namedCommentList.add(commentSaveForm);
        }
        return namedCommentList;
    }
}
