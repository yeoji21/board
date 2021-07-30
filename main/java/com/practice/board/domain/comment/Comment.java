package com.practice.board.domain.comment;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    private Long id;
    private Long memberId;
    private Long postId;
    private String content;
    private Date date;
}
