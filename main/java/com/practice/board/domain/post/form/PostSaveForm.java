package com.practice.board.domain.post.form;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveForm {
    @NotNull
    private String title;
    @NotNull
    private String content;

}
