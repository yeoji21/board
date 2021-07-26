package com.practice.board.domain.post;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private Long memberId;
    private String name;
    private String title;
    private String content;
    private Date postDate;

    public Post(Long memberId, String title, String content, Date postDate) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.postDate = postDate;
    }
}
