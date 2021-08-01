package com.practice.board.domain.comment.form;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentSaveForm {
    private Long id;
    private String name;
    private String comment;
    @DateTimeFormat(pattern = "MM-dd [HH:mm]")
    private Date date;
}
