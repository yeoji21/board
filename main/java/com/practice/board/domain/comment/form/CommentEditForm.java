package com.practice.board.domain.comment.form;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentEditForm {
    private Long cid;
    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd [HH:mm]")
    private Date date;
}
