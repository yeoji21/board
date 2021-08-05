package com.practice.board.domain.comment.form;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentAddForm {
    private Long memberId;
    @NotBlank
    private String comment;
}
