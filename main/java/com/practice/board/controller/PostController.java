package com.practice.board.controller;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.form.SessionConst;
import com.practice.board.domain.post.Post;
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
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostMapper postMapper;
    private final PostService postService;

    @GetMapping
    public String pageList(@RequestParam(value = "page", defaultValue ="1") int page, Model model) {
//        if (page == 0) {
//            page = 1;
//        }
        model.addAttribute("lists", postService.postPage(page));
        model.addAttribute("pages", postMapper.postCount() / 5 + 1);
        return "post/list";

    }

    @GetMapping("/add")
    public String addPost(@ModelAttribute("post") PostSaveForm postSaveForm) {
        return "post/addForm";
    }

    @PostMapping("/add")
    public String savePost(@Validated @ModelAttribute("post") PostSaveForm postSaveForm, BindingResult bindingResult,
                           HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.warn("errors={}", bindingResult);
            return "post/addForm";
        }

        Post post = new Post();
        HttpSession session = request.getSession(false);
        setPostMemberId(post, session);
        postService.setName(post, post.getMemberId());
        post.setTitle(postSaveForm.getTitle());
        post.setContent(postSaveForm.getContent());
        log.warn("post ={}", post.toString());
        postMapper.savePost(post);

        redirectAttributes.addAttribute("id", post.getId());
        return "redirect:/post/{id}";
    }

    private void setPostMemberId(Post post, HttpSession session) {
        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        post.setMemberId(loginMember.getId());
    }

    @GetMapping("/{id}")
    public String readPost(@PathVariable Long id, Model model) {
        Post post = postMapper.getPost(id);
        model.addAttribute("post", post);
        log.warn("post={}", post);
        return "post/post";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@ModelAttribute("post") PostSaveForm postSaveForm, @PathVariable("id") Long id, Model model) {
        Post post = postMapper.getPost(id);
        model.addAttribute("post", post);
        return "post/editForm";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Validated @ModelAttribute("post") PostSaveForm postSaveForm,
                       @PathVariable("id")Long id) {
        postMapper.updatePost(id, postSaveForm);
        return "redirect:/post/{id}";
    }
}
