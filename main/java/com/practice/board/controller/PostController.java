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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String postList(Model model) {
        List<Post> list = postMapper.postList();
        model.addAttribute("lists", list);
        log.warn("list ={}", list);
        return "post/list";
    }

    @GetMapping("/add")
    public String addPost(@ModelAttribute("post")PostSaveForm postSaveForm) {
        return "post/addForm";
    }

    @PostMapping("/add")
    public String savePost(@Validated @ModelAttribute("post")PostSaveForm postSaveForm, BindingResult bindingResult,
                           HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            log.warn("errors={}", bindingResult);
            return "post/addForm";
        }

        Post post = new Post();
        HttpSession session = request.getSession();
        //이거 되면 메소드화
        Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        post.setMemberId(loginMember.getId());
        postService.setName(post, post.getMemberId());
        post.setTitle(postSaveForm.getTitle());
        post.setContent(postSaveForm.getContent());
        log.warn("post ={}", post.toString());
        postMapper.savePost(post);
        return "redirect:/post";
    }
}
