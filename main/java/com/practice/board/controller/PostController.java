package com.practice.board.controller;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.SessionConst;
import com.practice.board.domain.post.Post;
import com.practice.board.domain.post.form.PostEditForm;
import com.practice.board.domain.post.form.PostSaveForm;
import com.practice.board.mapper.PostMapper;
import com.practice.board.service.PostService;
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
import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostMapper postMapper;
    private final PostService postService;

    @GetMapping
    public String pageList(@RequestParam(value = "page", defaultValue ="1") int page, Model model) {
        model.addAttribute("lists", postService.getFivePost(page));
        model.addAttribute("pages", postService.totalPages());
        return "post/list";
    }

    @GetMapping("/add")
    public String addPost(@ModelAttribute("post") PostSaveForm postSaveForm) {
        return "post/addForm";
    }

    @PostMapping("/add")
    public String savePost(@Validated @ModelAttribute("post") PostSaveForm postSaveForm, BindingResult bindingResult,
                           HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (postErrorCheck(bindingResult)) {
            log.warn("errors={}", bindingResult);
            return "post/addForm";
        }

        Post post = postSaveFormToPost(postSaveForm, request);
        postMapper.savePost(post);
        redirectAttributes.addAttribute("id", post.getId());
        return "redirect:/post/{id}";
    }

    @GetMapping("/{id}")
    public String readPost(@PathVariable Long id, Model model) {
        model.addAttribute("post", postMapper.getPost(id));
        return "post/post";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@ModelAttribute("post") PostEditForm postEditForm, @PathVariable("id") Long id,
                           Model model) {
        model.addAttribute("post", postMapper.getPost(id));
        return "post/editForm";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Validated @ModelAttribute("post") PostEditForm postEditForm, BindingResult bindingResult,
                       @PathVariable("id") Long id, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            log.warn("errors = {}", bindingResult);
            return "post/editForm";
        }
        HttpSession session = request.getSession(false);
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        postEditForm.setName(member.getName());
        Date date = new Date();
        postEditForm.setPostDate(date);
        postMapper.updatePost(id, postEditForm);
        return "redirect:/post/{id}";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id")Long id) {
        postMapper.deletePost(id);
        return "redirect:/post";
    }

    private Post postSaveFormToPost(PostSaveForm postSaveForm, HttpServletRequest request) {
        Post post = new Post();
        setPostMemberId(post, request.getSession(false));
        postService.setName(post, post.getMemberId());
        post.setTitle(postSaveForm.getTitle());
        post.setContent(postSaveForm.getContent());
        return post;
    }

    private boolean postErrorCheck(BindingResult bindingResult){
        return bindingResult.hasErrors();
    }

    private void setPostMemberId(Post post, HttpSession session) {
        if (session == null) {
            return;
        }
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        post.setMemberId(loginMember.getId());
    }
}
