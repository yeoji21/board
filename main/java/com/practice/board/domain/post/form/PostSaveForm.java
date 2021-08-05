package com.practice.board.domain.post.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveForm {
    @NotBlank
    @Length(max=25)
    private String title;
    @NotNull
    private String content;

}
