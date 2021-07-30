package com.practice.board.domain.comment;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String comment;
    private Date date;
}
